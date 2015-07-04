package laxstats.web.players;

import laxstats.TestUtils;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;
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

public class PlayerValidator implements Validator {
   private static Logger logger = LoggerFactory.getLogger(PlayerValidator.class);
   private static String PACKAGE_NAME = PlayerValidator.class.getPackage().getName();

   @Autowired
   TeamSeasonQueryRepository teamSeasonQueryRepository;
   @Autowired
   PlayerQueryRepository playerQueryRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return PlayerResource.class.equals(clazz) || PlayerForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(target, errors);
      logger.debug(proc + "20");

      // Validates that the team is not inactive
      checkTeamStatus(target, errors);

      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors0
    */
   private void checkMandatoryArgs(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      String personId = null;
      String teamSeasonId = null;
      Role role = null;
      PlayerStatus status = null;
      Position position = null;

      if (target instanceof PlayerResource) {
         final PlayerResource resource = (PlayerResource)target;
         personId = resource.getPerson();
         teamSeasonId = resource.getTeamSeason();
         role = resource.getRole();
         status = resource.getStatus();
         position = resource.getPosition();
      }
      else if (target instanceof PlayerForm) {
         final PlayerForm form = (PlayerForm)target;
         personId = form.getPerson();
         teamSeasonId = form.getTeamSeason();
         role = form.getRole();
         status = form.getStatus();
         position = form.getPosition();
      }

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
   private void checkTeamStatus(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkTeamStatus.";
      String playerId = null;
      String teamSeasonId = null;
      boolean doValidation = false;

      if (target instanceof PlayerResource) {
         final PlayerResource resource = (PlayerResource)target;
         playerId = resource.getId();
         teamSeasonId = resource.getTeamSeason();
      }
      else if (target instanceof PlayerForm) {
         final PlayerForm form = (PlayerForm)target;
         playerId = form.getId();
         teamSeasonId = form.getTeamSeason();
      }
      final TeamSeasonEntry teamSeason = teamSeasonQueryRepository.findOne(teamSeasonId);

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(playerId);
      logger.debug(proc + "20");

      // Only proceed is the record is new or if the player's team has changed.
      if (isUpdating) {
         logger.debug(proc + "30");

         final PlayerEntry player = playerQueryRepository.findOne(playerId);
         if (!teamSeasonId.equals(player.getTeamSeason().getId())) {
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
