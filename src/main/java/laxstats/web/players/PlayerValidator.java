package laxstats.web.players;

import laxstats.TestUtils;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.api.utils.Common;
import laxstats.query.players.PlayerEntry;
import laxstats.query.players.PlayerQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PlayerValidator implements Validator {
   private static Logger logger = LoggerFactory.getLogger(PlayerValidator.class);
   private static String PACKAGE_NAME = PlayerValidator.class.getPackage()
      .getName();

   @Autowired
   TeamSeasonQueryRepository teamSeasonQueryRepository;
   @Autowired
   PlayerQueryRepository playerQueryRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return PlayerResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final PlayerResource resource = (PlayerResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validates that the team is not inactive
      checkTeamStatus(resource, errors);
      logger.debug(proc + "30");

      // Check parent release sent date
      checkParentReleaseSentOn(resource, errors);
      logger.debug(proc + "40");

      // Check parent release received date
      checkParentReleaseReceivedOn(resource, errors);
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors0
    */
   private void checkMandatoryArgs(PlayerResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String personId = target.getPerson();
      final String teamSeasonId = target.getTeamSeason();
      final Role role = target.getRole();
      final PlayerStatus status = target.getStatus();
      final Position position = target.getPosition();

      logger.debug("Entering: " + proc + "10");

      if (TestUtils.isEmptyOrWhitespace(personId)) {
         errors.rejectValue("person", "player.person.required");
      }
      logger.debug(proc + "20");

      if (TestUtils.isEmptyOrWhitespace(teamSeasonId)) {
         errors.rejectValue("teamSeason", "player.teamSeason.required");
      }
      logger.debug(proc + "30");

      if (role == null) {
         errors.rejectValue("role", "player.role.required");
      }
      logger.debug(proc + "40");

      if (status == null) {
         errors.rejectValue("status", "player.status.required");
      }
      logger.debug(proc + "50");

      if (position == null) {
         errors.rejectValue("position", "player.position.required");
      }

      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the given player's assigned team is not inactive.
    *
    * @param form
    * @param errors
    */
   private void checkTeamStatus(PlayerResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkTeamStatus.";
      final String playerId = target.getId();
      final String teamSeasonId = target.getTeamSeason();
      boolean doValidation = false;
      final TeamSeasonEntry teamSeason = teamSeasonQueryRepository.findOne(teamSeasonId);

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(playerId);
      logger.debug(proc + "20");

      // Only proceed is the record is new or if the player's team has changed.
      if (isUpdating) {
         logger.debug(proc + "30");

         final PlayerEntry player = playerQueryRepository.findOne(playerId);
         if (!teamSeasonId.equals(player.getTeamSeason()
            .getId())) {
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "40");

         doValidation = true;
      }

      if (doValidation && teamSeason != null) {
         logger.debug(proc + "50");

         if (teamSeason.getStatus()
            .equals(TeamStatus.INACTIVE)) {
            errors.rejectValue("status", "player.status.inactiveTeamSeason");
         }
      }

      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the date the parent release was sent. If the parent release sent date is before
    * the received on date , or if the received on date is null, the processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkParentReleaseSentOn(PlayerResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkParentReleaseSentOn.";
      final String playerId = target.getId();
      final LocalDate sentOn = target.getParentReleaseSentOnAsLocalDate();
      final LocalDate receivedOn = target.getParentReleaseReceivedOnAsLocalDate();
      final LocalDate eot = Common.EOT.toLocalDate();

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(playerId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final PlayerEntry player = playerQueryRepository.findOne(playerId);
         if (!Common.nvl(player.getParentReleaseSentOn(), eot)
            .equals(Common.nvl(sentOn, eot))) {
            logger.debug(proc + "40");

            if (Common.nvl(sentOn, eot)
               .isAfter(Common.nvl(receivedOn, eot))) {
               errors.rejectValue("parentReleaseSentOn", "player.parentReleaseSentOn.invalid");
            }
         }
      }
      else {
         logger.debug(proc + "50");
         if (Common.nvl(sentOn, eot)
            .isAfter(Common.nvl(receivedOn, eot))) {
            errors.rejectValue("parentReleaseSentOn", "player.parentReleaseSentOn.invalid");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates the date the parent release was received. If the parent release received date is
    * after the sent date, the processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkParentReleaseReceivedOn(PlayerResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkParentReleaseReceivedOn.";
      final String playerId = target.getId();
      final LocalDate sentOn = target.getParentReleaseSentOnAsLocalDate();
      final LocalDate receivedOn = target.getParentReleaseReceivedOnAsLocalDate();
      final LocalDate eot = Common.EOT.toLocalDate();

      logger.debug("Entering: " + proc + "10");

      if (receivedOn != null) {
         logger.debug(proc + "20");

         if (sentOn == null) {
            errors.rejectValue("parentReleaseSentOn", "player.parentReleaseSentOn.required");
         }

         final boolean isUpdating = apiUpdating(playerId);
         logger.debug(proc + "30");

         if (isUpdating) {
            logger.debug(proc + "40");

            final PlayerEntry player = playerQueryRepository.findOne(playerId);
            if (!Common.nvl(player.getParentReleaseReceivedOn(), eot)
               .equals(Common.nvl(receivedOn, eot))) {
               logger.debug(proc + "50");

               if (Common.nvl(receivedOn, eot)
                  .isBefore(Common.nvl(sentOn, eot))) {
                  errors.rejectValue("parentReleaseReceivedOn",
                     "player.parentReleaseReceivedOn.invalid");
               }
            }

         }
         else {
            logger.debug(proc + "60");
            if (Common.nvl(receivedOn, eot)
               .isBefore(Common.nvl(sentOn, eot))) {
               errors.rejectValue("parentReleaseReceivedOn",
                  "player.parentReleaseReceivedOn.invalid");
            }
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Returns true if the record with the given primary key is being updated, false otherwise.
    *
    * @param id
    * @return
    */
   private boolean apiUpdating(String id) {
      boolean result = false;
      if (id == null) {
         result = false;
      }
      else {
         final boolean exists = playerQueryRepository.exists(id);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
