package laxstats.web.teamSeasons;

import laxstats.api.Common;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class TeamSeasonFormValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(TeamSeasonFormValidator.class);
   private static final String PACKAGE_NAME = TeamSeasonFormValidator.class.getPackage().getName();

   @Autowired
   private TeamSeasonQueryRepository teamRepository;
   @Autowired
   private SeasonQueryRepository seasonRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return TeamSeasonForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final TeamSeasonForm form = (TeamSeasonForm)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(form, errors);
      logger.debug(proc + "20");

      // Validate non-updateable arguments
      checkNonUpdateableArgs(form, errors);
      logger.debug(proc + "30");

      // Validate team/season combination
      checkDuplicate(form, errors);
      logger.debug(proc + "40");

      // Validate start date
      checkStartDate(form, errors);
      logger.debug(proc + "50");

      // Validate end date
      checkEndDate(form, errors);
      logger.debug(proc + "60");

      // Validate start and end dates
      checkDates(form, errors);

      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Validates that mandatory parameters have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(TeamSeasonForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

      logger.debug("Entering: " + proc + "10");

      if (form.getTeam() == null) {
         errors.rejectValue("team", "teamSeason.team.required");
      }
      logger.debug(proc + "20");

      if (form.getSeason() == null) {
         errors.rejectValue("season", "teamSeason.season.required");
      }
      logger.debug(proc + "30");

      if (form.getStatus() == null) {
         errors.rejectValue("status", "teamSeason.status.required");
      }

      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that non-updateable values have not changed. Specifically, this method validates
    * that the team and season have not changed if games are associated with this record.
    *
    * @param form
    * @param errors
    */
   private void checkNonUpdateableArgs(TeamSeasonForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkNonUpdateableArgs.";
      final String teamSeasonId = form.getId();
      final String teamId = form.getTeam();
      final String seasonId = form.getSeason();

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(teamSeasonId);
      if (isUpdating) {
         logger.debug(proc + "20");

         final TeamSeasonEntry team = teamRepository.findOne(teamSeasonId);
         if (team.getEvents().size() > 0) {
            logger.debug(proc + "30");

            if (teamId == null || !team.getTeam().getId().equals(teamId)) {
               errors.rejectValue("team", "teamSeason.team.notUpdateable");
            }
            logger.debug(proc + "40");

            if (seasonId == null || !team.getSeason().getId().equals(seasonId)) {
               errors.rejectValue("season", "teamSeason.season.notUpdateable");
            }
         }
      }
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that the team and season combination are unique.
    *
    * @param form
    * @param errors
    */
   private void checkDuplicate(TeamSeasonForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDuplicate.";
      final String teamSeasonId = form.getId();
      final String teamId = form.getTeam();
      final String seasonId = form.getSeason();
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the team or
      // season has changed.

      final boolean isUpdating = apiUpdating(teamSeasonId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final TeamSeasonEntry team = teamRepository.findOne(teamSeasonId);
         if (!team.getTeam().getId().equals(teamId) || !team.getSeason().getId().equals(seasonId)) {
            logger.debug(proc + "40");

            found = teamRepository.updateSeason(teamId, seasonId, teamSeasonId);
            if (found > 0) {
               errors.rejectValue("team", "teamSeason.duplicate");
            }
         }
      }
      else if (teamId != null && seasonId != null) {
         logger.debug(proc + "50");

         found = teamRepository.uniqueTeamSeason(teamId, seasonId);
         if (found > 0) {
            errors.rejectValue("team", "teamSeason.duplicate");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the team season start date, if set, is within the specified season date range.
    *
    * @param form
    * @param errors
    */
   private void checkStartDate(TeamSeasonForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkStartDate.";
      final String teamSeasonId = form.getId();
      final LocalDate startsOn = form.getStartsOn();
      final SeasonEntry season = seasonRepository.findOne(form.getSeason());
      final LocalDate eot = Common.EOT.toLocalDate();

      logger.debug("Entering: " + proc + "10");

      if (startsOn != null && season != null) {
         // Only proceed with validation if the record is new or if the start
         // date has changed.

         final boolean isUpdating = apiUpdating(teamSeasonId);
         logger.debug(proc + "20");

         if (isUpdating) {
            logger.debug(proc + "30");

            final TeamSeasonEntry team = teamRepository.findOne(teamSeasonId);
            if (!team.getStartsOn().equals(startsOn)) {
               logger.debug(proc + "40");

               if (startsOn.isBefore(season.getStartsOn())) {
                  errors.rejectValue("startsOn", "teamSeason.startsOn.tooEarly");
               }
               else if (startsOn.isAfter(Common.nvl(season.getEndsOn(), eot))) {
                  errors.rejectValue("startsOn", "teamSeason.startsOn.tooLate");
               }
            }
         }
         else {
            logger.debug(proc + "50");

            if (startsOn.isBefore(season.getStartsOn())) {
               errors.rejectValue("startsOn", "teamSeason.startsOn.tooEarly");
            }
            else if (startsOn.isAfter(Common.nvl(season.getEndsOn(), eot))) {
               errors.rejectValue("startsOn", "teamSeason.startsOn.tooLate");
            }
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the team season end date, if set, is within the specified season date range.
    *
    * @param form
    * @param errors
    */
   private void checkEndDate(TeamSeasonForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkEndDate.";
      final String teamSeasonId = form.getId();
      final LocalDate endsOn = form.getEndsOn();
      final SeasonEntry season = seasonRepository.findOne(form.getSeason());
      final LocalDate eot = Common.EOT.toLocalDate();

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the end date
      // has changed.

      final boolean isUpdating = apiUpdating(teamSeasonId);
      logger.debug(proc + "20");

      if (season != null) {
         if (isUpdating) {
            logger.debug(proc + "30");

            final TeamSeasonEntry team = teamRepository.findOne(teamSeasonId);
            if (!Common.nvl(team.getEndsOn(), eot).equals(Common.nvl(endsOn, eot))) {
               logger.debug(proc + "40");

               if (Common.nvl(endsOn, eot).isBefore(season.getStartsOn())) {
                  errors.rejectValue("endsOn", "teamSeason.endsOn.tooEarly");
               }
               else if (Common.nvl(endsOn, eot).isAfter(Common.nvl(season.getEndsOn(), eot))) {
                  errors.rejectValue("endsOn", "teamSeason.endsOn.tooLate");
               }
            }
         }
         else {
            logger.debug(proc + "50");

            if (Common.nvl(endsOn, eot).isBefore(season.getStartsOn())) {
               errors.rejectValue("endsOn", "teamSeason.endsOn.tooEarly");
            }
            else if (Common.nvl(endsOn, eot).isAfter(Common.nvl(season.getEndsOn(), eot))) {
               errors.rejectValue("endsOn", "teamSeason.endsOn.tooLate");
            }
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the team season start date, if set, is not earlier than the end date.
    *
    * @param form
    * @param errors
    */
   private void checkDates(TeamSeasonForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDates.";
      final String teamSeasonId = form.getId();
      final LocalDate startsOn = form.getStartsOn();
      final LocalDate endsOn = form.getEndsOn();
      final LocalDate eot = Common.EOT.toLocalDate();

      logger.debug("Entering: " + proc + "10");

      if (startsOn != null) {
         logger.debug(proc + "20");

         // Only proceed with validation if the record is new or if the start
         // or end dates have changed.

         final boolean isUpdating = apiUpdating(teamSeasonId);
         if (isUpdating) {
            logger.debug(proc + "30");

            final TeamSeasonEntry team = teamRepository.findOne(teamSeasonId);
            if (!team.getStartsOn().equals(startsOn) ||
                     !Common.nvl(team.getEndsOn(), eot).equals(Common.nvl(endsOn, eot))) {
               logger.debug(proc + "40");

               if (startsOn.isAfter(Common.nvl(endsOn, eot))) {
                  errors.rejectValue("startsOn", "teamSeason.invalidDateRange");
               }
            }
         }
         else {
            logger.debug(proc + "50");

            if (startsOn.isAfter(Common.nvl(endsOn, eot))) {
               errors.rejectValue("startsOn", "teamSeason.invalidDateRange");
            }
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Returns <code>true</code> if the record with the given primary key is being updated,
    * <code>false</code> otherwise.
    *
    * @param teamSeasonId
    * @return
    * @throws IllegalStateException if no record exists with the given primary key.
    */
   private boolean apiUpdating(String teamSeasonId) {
      boolean result = false;
      if (teamSeasonId == null) {
         result = false;
      }
      else {
         final boolean exists = teamRepository.exists(teamSeasonId);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
