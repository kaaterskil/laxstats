package laxstats.web.games;

import laxstats.api.games.Schedule;
import laxstats.api.games.Status;
import laxstats.api.sites.SiteAlignment;
import laxstats.api.utils.Common;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.TeamEvent;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class GameValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(GameValidator.class);
   private static final String PACKAGE_NAME = GameValidator.class.getPackage().getName();

   @Autowired
   private GameQueryRepository gameRepository;
   @Autowired
   private TeamQueryRepository teamRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return GameResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final GameResource resource = (GameResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory args
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate uniqueness
      checkDuplicate(resource, errors);
      logger.debug(proc + "30");

      // Validate team seasons
      checkTeamSeason(resource, errors);
      logger.debug(proc + "40");

      // Validate same team
      checkSameTeam(resource, errors);
      logger.debug(proc + "50");

      // Validate home team
      checkAlignment(resource, errors);
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(GameResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final LocalDateTime startsAt = target.getStartsAtAsDateTime();
      final Schedule schedule = target.getSchedule();
      final Status status = target.getStatus();

      logger.debug("Entering: " + proc + "10");

      if (startsAt == null) {
         errors.rejectValue("startsAt", "game.startsAt.required");
      }
      logger.debug(proc + "20");

      if (schedule == null) {
         errors.rejectValue("schedule", "game.schedule.required");
      }
      logger.debug(proc + "30");

      if (status == null) {
         errors.rejectValue("status", "game.status.required");
      }
      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that the game date/time, site and team combination are unique.
    *
    * @param form
    * @param errors
    */
   private void checkDuplicate(GameResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDuplicate.";
      final String gameId = target.getId();
      final String siteId = target.getSite();
      final String teamOneId = target.getTeamOne();
      final String teamTwoId = target.getTeamTwo();
      final String startsAt = target.getStartsAt();
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation is the record is new or if the teams
      // have changed.

      final boolean isUpdating = apiUpdating(gameId);
      if (isUpdating) {
         final GameEntry game = gameRepository.findOne(gameId);
         if (!game.getTeams().isEmpty()) {
            logger.debug(proc + "20");

            final TeamEvent oldTeamOne = game.getTeams().get(0);
            final TeamEvent oldTeamTwo = game.getTeams().get(1);
            if ((!Common.nvl(oldTeamOne.getId(), "").equals(Common.nvl(teamOneId, ""))) ||
               (!Common.nvl(oldTeamTwo.getId(), "").equals(Common.nvl(teamTwoId, "")))) {
               logger.debug(proc + "30");

               found = gameRepository.checkUpdate(startsAt, siteId, teamOneId, teamTwoId, gameId);
               if (found > 0) {
                  errors.rejectValue("startsAt", "game.duplicate");
               }
            }
         }
      }
      else {
         logger.debug(proc + "40");
         found = gameRepository.checkUnique(startsAt, siteId, teamOneId, teamTwoId);
         if (found > 0) {
            errors.rejectValue("startsAt", "game.duplicate");
         }
      }
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that both teams are not the same team.
    *
    * @param form
    * @param errors
    */
   private void checkSameTeam(GameResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkSameTeam.";
      final String gameId = target.getId();
      final String teamOneId = target.getTeamOne();
      final String teamTwoId = target.getTeamTwo();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or if the teams
      // have changed.

      final boolean isUpdating = apiUpdating(gameId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final GameEntry game = gameRepository.findOne(gameId);
         if (!game.getTeams().isEmpty()) {
            logger.debug(proc + "40");

            final TeamEvent oldTeamOne = game.getTeams().get(0);
            final TeamEvent oldTeamTwo = game.getTeams().get(1);
            if ((!Common.nvl(oldTeamOne.getId(), "").equals(Common.nvl(teamOneId, ""))) ||
               (!Common.nvl(oldTeamTwo.getId(), "").equals(Common.nvl(teamTwoId, "")))) {
               logger.debug(proc + "50");
               doValidation = true;
            }
         }
      }
      else {
         logger.debug(proc + "60");
         doValidation = true;
      }

      if (doValidation) {
         if (teamOneId != null && teamTwoId != null && teamOneId.equals(teamTwoId)) {
            errors.rejectValue("teamTwo", "game.sameTeam");
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Validates that each team has an associated season for the given start date.
    *
    * @param form
    * @param errors
    */
   private void checkTeamSeason(GameResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkTeamSeason.";
      final String gameId = target.getId();
      final String teamOneId = target.getTeamOne();
      final String teamTwoId = target.getTeamTwo();
      final LocalDateTime startsAt = target.getStartsAtAsDateTime();
      String oldTeamOneId = null;
      String oldTeamTwoId = null;
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or if the teams
      // have changed.

      final boolean isUpdating = apiUpdating(gameId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final GameEntry game = gameRepository.findOne(gameId);
         if (!game.getTeams().isEmpty()) {
            logger.debug(proc + "40");

            final TeamEvent oldTeamOne = game.getTeams().get(0);
            if (oldTeamOne != null) {
               oldTeamOneId = oldTeamOne.getId();
            }
            final TeamEvent oldTeamTwo = game.getTeams().get(1);
            if (oldTeamTwo != null) {
               oldTeamTwoId = oldTeamTwo.getId();
            }
            if ((!Common.nvl(oldTeamOneId, null).equals(Common.nvl(teamOneId, null))) ||
               (!Common.nvl(oldTeamTwoId, null).equals(Common.nvl(teamTwoId, null)))) {
               logger.debug(proc + "50");
               doValidation = true;
            }
         }
      }
      else {
         logger.debug(proc + "60");
         doValidation = true;
      }

      if (doValidation) {
         if (teamOneId != null) {
            logger.debug(proc + "70");

            final TeamEntry teamOne = teamRepository.findOne(teamOneId);
            if (teamOne.getSeason(startsAt) == null) {
               errors.rejectValue("teamOne", "event.nullTeamOneSeason");
            }
         }
         if (teamTwoId != null) {
            logger.debug(proc + "80");

            final TeamEntry teamTwo = teamRepository.findOne(teamTwoId);
            if (teamTwo.getSeason(startsAt) == null) {
               errors.rejectValue("teamOne", "event.nullTeamTwoSeason");
            }
         }
      }
      logger.debug("Leaving: " + proc + "90");
   }

   /**
    * Validates that only a single team is classified as a home team if the site is a designated
    * home site, and that there is no home team if the site not a home site.
    *
    * @param form
    * @param errors
    */
   private void checkAlignment(GameResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkAlignment.";
      final String gameId = target.getId();
      final String teamOneId = target.getTeamOne();
      final String teamTwoId = target.getTeamTwo();
      final boolean isTeamOneHome = target.isTeamOneHome();
      final boolean isTeamTwoHome = target.isTeamTwoHome();
      final SiteAlignment alignment = target.getAlignment();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or if the teams
      // have changed.

      final boolean isUpdating = apiUpdating(gameId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final GameEntry game = gameRepository.findOne(gameId);
         if (!game.getTeams().isEmpty()) {
            logger.debug(proc + "40");

            final TeamEvent oldTeamOne = game.getTeams().get(0);
            final TeamEvent oldTeamTwo = game.getTeams().get(1);
            if ((!Common.nvl(oldTeamOne.getId(), "").equals(Common.nvl(teamOneId, ""))) ||
               (!Common.nvl(oldTeamTwo.getId(), "").equals(Common.nvl(teamTwoId, "")))) {
               logger.debug(proc + "50");
               doValidation = true;
            }
         }
      }
      else {
         logger.debug(proc + "60");
         doValidation = true;
      }

      if (doValidation && teamOneId != null && teamTwoId != null) {
         if (alignment.equals(SiteAlignment.HOME)) {
            logger.debug(proc + "70");

            if (!isTeamOneHome && !isTeamTwoHome) {
               logger.debug(proc + "72");
               errors.rejectValue("teamOneHome", "game.teamOneHome.required");
            }
            if (isTeamOneHome && isTeamTwoHome) {
               logger.debug(proc + "74");
               errors.rejectValue("teamOneHome", "game.teamOneHome.twoHomeTeams");
            }
         }
         else {
            logger.debug(proc + "80");

            if (isTeamOneHome) {
               logger.debug(proc + "82");
               errors.rejectValue("teamOneHome", "game.teamOneHome.invalid");
            }
            if (isTeamTwoHome) {
               logger.debug(proc + "84");
               errors.rejectValue("teamTwoHome", "game.teamTwoHome.invalid");
            }
         }
      }
      logger.debug("Leaving: " + proc + "90");
   }

   /**
    * Returns true if the record with the given primary key is being updated, false otherwise.
    *
    * @param gameId
    * @return
    * @throws IllegalStateException if no records exists for the given primary key.
    */
   private boolean apiUpdating(String gameId) {
      boolean result = false;
      if (gameId == null) {
         result = false;
      }
      else {
         final boolean exists = gameRepository.exists(gameId);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
