package laxstats.web.games;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.GroundBallEntry;
import laxstats.query.games.PlayParticipantEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class GroundBallValidator extends AbstractPlayValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(GroundBallValidator.class);
   private static final String PACKAGE_NAME = GroundBallValidator.class.getPackage().getName();

   @Autowired
   GameQueryRepository gameRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return GroundBallResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final GroundBallResource resource = (GroundBallResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory args
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate non-updateable args
      checkNonUpdateableArgs(resource, errors);
      logger.debug(proc + "30");

      // Validate period
      checkPeriod(resource, errors);
      logger.debug(proc + "40");

      // Validate team
      checkTeam(resource, errors);
      logger.debug(proc + "50");

      // Validate player
      checkPlayer(resource, errors);
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param target
    * @param errors
    */
   private void checkMandatoryArgs(GroundBallResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String teamSeasonId = target.getTeamSeasonId();
      final String playerId = target.getPlayerId();

      logger.debug("Entering: " + proc + "10");

      if (teamSeasonId == null) {
         errors.rejectValue("teamSeasonId", "play.teamSeasonId.required");
      }
      logger.debug(proc + "20");

      if (playerId == null) {
         errors.rejectValue("playerId", "groundBall.playerId.required");
      }
      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that the faceoff winner is a valid player. If the player is registered as a game
    * attendee and is an athlete who is not inactive or injured, then processing continues.
    *
    * @param target
    * @param errors
    * @throws IllegalStateException if the player is not registered as a game attendee.
    */
   private void checkPlayer(GroundBallResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkPlayer.";
      final String playId = target.getPlayId();
      final String gameId = target.getGameId();
      final String attendeeId = target.getPlayerId();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the player
      // has changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final GroundBallEntry play = (GroundBallEntry)getPlayRepository().findOne(playId);
         PlayParticipantEntry oldAttendee = null;
         if (!play.getParticipants().isEmpty()) {
            oldAttendee = play.getParticipants().get(0);
         }
         logger.debug(proc + "40");

         if (oldAttendee == null || !oldAttendee.getId().equals(attendeeId)) {
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation) {
         final GameEntry game = gameRepository.findOne(gameId);
         logger.debug(proc + "60");

         final AttendeeEntry attendee = game.getAttendee(attendeeId);
         if (attendee == null) {
            throw new IllegalStateException("Invalid player");
         }
         logger.debug(proc + "70");

         if (!attendee.getPlayer().getRole().equals(Role.ATHLETE)) {
            errors.rejectValue("playerId", "play.playerId.invalidPlayer");
         }
         logger.debug(proc + "80");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INACTIVE)) {
            errors.rejectValue("winnerId", "play.playerId.inactivePlayer");
         }
         logger.debug(proc + "90");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INJURED)) {
            errors.rejectValue("playerId", "play.playerId.injuredPlayer");
         }
      }
      logger.debug("Leaving: " + proc + "100");
   }

}
