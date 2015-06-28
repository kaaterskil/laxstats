package laxstats.web.players;

import laxstats.TestUtils;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.query.players.PlayerEntry;
import laxstats.query.players.PlayerQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PlayerFormValidator implements Validator {
   private static Logger logger = LoggerFactory.getLogger(PlayerFormValidator.class);
   private static String PACKAGE_NAME = PlayerFormValidator.class.getPackage().getName();

   @Autowired
   TeamSeasonQueryRepository teamSeasonQueryRepository;
   @Autowired
   PlayerQueryRepository playerQueryRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return PlayerForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final PlayerForm form = (PlayerForm)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(form, errors);
      logger.debug(proc + "20");

      // Validates that the team is not inactive
      checkTeamStatus(form, errors);

      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors0
    */
   private void checkMandatoryArgs(PlayerForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

      logger.debug("Entering: " + proc + "10");

      if (TestUtils.isEmptyOrWhitespace(form.getPerson())) {
         errors.rejectValue("person", "player.person.required");
      }
      logger.debug(proc + "20");

      if (TestUtils.isEmptyOrWhitespace(form.getTeamSeason())) {
         errors.rejectValue("teamSeason", "player.teamSeason.required");
      }
      logger.debug(proc + "30");

      if (form.getRole() == null) {
         errors.rejectValue("role", "player.role.required");
      }
      logger.debug(proc + "40");

      if (form.getStatus() == null) {
         errors.rejectValue("status", "player.status.required");
      }
      logger.debug(proc + "50");

      if (form.getPosition() == null) {
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
   private void checkTeamStatus(PlayerForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkTeamStatus.";
      final String playerId = form.getId();
      final TeamSeasonEntry teamSeason = teamSeasonQueryRepository.findOne(form.getTeamSeason());
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(playerId);
      logger.debug(proc + "20");

      // Only proceed is the record is new or if the player's team has changed.
      if (isUpdating) {
         logger.debug(proc + "30");

         final PlayerEntry player = playerQueryRepository.findOne(playerId);
         if (!form.getTeamSeason().equals(player.getTeamSeason().getId())) {
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "40");

         doValidation = true;
      }

      if (doValidation && teamSeason != null) {
         logger.debug(proc + "50");

         if (teamSeason.getStatus().equals(TeamStatus.INACTIVE)) {
            errors.rejectValue("status", "player.status.inactiveTeamSeason");
         }
      }

      logger.debug("Leaving: " + proc + "60");
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
