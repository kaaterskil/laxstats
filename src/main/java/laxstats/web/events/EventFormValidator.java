package laxstats.web.events;

import laxstats.api.Common;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.events.GameEntry;
import laxstats.query.events.GameQueryRepository;
import laxstats.query.events.TeamEvent;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class EventFormValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(EventFormValidator.class);
   private static final String PACKAGE_NAME = EventFormValidator.class.getPackage().getName();

   @Autowired
   private GameQueryRepository gameRepository;
   @Autowired
   private TeamQueryRepository teamRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return EventForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final EventForm form = (EventForm)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory args
      checkMandatoryArgs(form, errors);
      logger.debug(proc + "20");

      // Validate uniqueness
      checkDuplicate(form, errors);
      logger.debug(proc + "30");

      // Validate team seasons
      checkTeamSeason(form, errors);
      logger.debug(proc + "40");

      // Validate same team
      checkSameTeam(form, errors);
      logger.debug(proc + "50");

      // Validate home team
      checkAlignment(form, errors);
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(EventForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

      logger.debug("Entering: " + proc + "10");

      ValidationUtils.rejectIfEmpty(errors, "startsAt", "game.startsAt.required");
      logger.debug(proc + "20");

      ValidationUtils.rejectIfEmpty(errors, "schedule", "game.schedule.required");
      logger.debug(proc + "30");

      ValidationUtils.rejectIfEmpty(errors, "status", "game.status.required");
      logger.debug(proc + "40");

      ValidationUtils.rejectIfEmpty(errors, "site", "game.site.required");
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that the game date/time, site and team combination are unique.
    *
    * @param form
    * @param errors
    */
   private void checkDuplicate(EventForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDuplicate.";
      final String gameId = form.getId();
      final String siteId = form.getSite();
      final String teamOneId = form.getTeamOne();
      final String teamTwoId = form.getTeamTwo();
      final String startsAt = form.getStartsAt().toString("yyyy-MM-dd HH:mm:ss");
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
   private void checkSameTeam(EventForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkSameTeam.";
      final String gameId = form.getId();
      final String teamOneId = form.getTeamOne();
      final String teamTwoId = form.getTeamTwo();
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
   private void checkTeamSeason(EventForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkTeamSeason.";
      final String gameId = form.getId();
      final String teamOneId = form.getTeamOne();
      final String teamTwoId = form.getTeamTwo();
      final LocalDateTime startsAt = form.getStartsAt();
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
            final TeamEvent oldTeamOne = game.getTeams().get(0);
            final TeamEvent oldTeamTwo = game.getTeams().get(1);
            if ((!Common.nvl(oldTeamOne.getId(), "").equals(Common.nvl(teamOneId, ""))) ||
               (!Common.nvl(oldTeamTwo.getId(), "").equals(Common.nvl(teamTwoId, "")))) {
               logger.debug(proc + "40");
               doValidation = true;
            }
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation) {
         if (teamOneId != null) {
            logger.debug(proc + "60");

            final TeamEntry teamOne = teamRepository.findOne(teamOneId);
            if (teamOne.getSeason(startsAt) == null) {
               errors.rejectValue("teamOne", "event.nullTeamOneSeason");
            }
         }
         if (teamTwoId != null) {
            logger.debug(proc + "70");

            final TeamEntry teamTwo = teamRepository.findOne(teamTwoId);
            if (teamTwo.getSeason(startsAt) == null) {
               errors.rejectValue("teamOne", "event.nullTeamTwoSeason");
            }
         }
      }
      logger.debug("Leaving: " + proc + "80");
   }

   /**
    * Validates that only a single team is classified as a home team if the site is a designated
    * home site, and that there is no home team if the site not a home site.
    *
    * @param form
    * @param errors
    */
   private void checkAlignment(EventForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkAlignment.";
      final String gameId = form.getId();
      final String teamOneId = form.getTeamOne();
      final String teamTwoId = form.getTeamTwo();
      final boolean isTeamOneHome = form.isTeamOneHome();
      final boolean isTeamTwoHome = form.isTeamTwoHome();
      final SiteAlignment alignment = form.getAlignment();
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
         doValidation = false;
      }

      if (doValidation) {
         if (alignment.equals(SiteAlignment.HOME)) {
            if (!isTeamOneHome && !isTeamTwoHome) {
               errors.rejectValue("teamOneHome", "game.teamOneHome.required");
            }
            if (isTeamOneHome && isTeamTwoHome) {
               errors.rejectValue("teamOneHome", "game.teamOneHome.twoHomeTeams");
            }
         }
         else {
            if (isTeamOneHome) {
               errors.rejectValue("teamOneHome", "game.teamOneHome.invalid");
            }
            if (isTeamTwoHome) {
               errors.rejectValue("teamTwoHome", "game.teamTwoHome.invalid");
            }
         }
      }
      logger.debug("Leaving: " + proc + "70");
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
