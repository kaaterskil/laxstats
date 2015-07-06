package laxstats.web.games;

import laxstats.api.games.PlayResult;
import laxstats.api.games.ScoreAttemptType;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.PlayParticipantEntry;
import laxstats.query.games.ShotEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ShotValidator extends AbstractPlayValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(ShotValidator.class);
   private static final String PACKAGE_NAME = ShotValidator.class.getPackage().getName();

   @Autowired
   GameQueryRepository gameRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return ShotResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final ShotResource resource = (ShotResource)target;

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
      logger.debug(proc + "60");

      // Validate attempt type
      checkAttemptType(resource, errors);
      logger.debug(proc + "70");

      // Validate result
      checkResult(resource, errors);
      logger.debug("Leaving: " + proc + "80");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(ShotResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String teamSeasonId = target.getTeamSeasonId();
      final String playerId = target.getPlayerId();
      final ScoreAttemptType type = target.getAttemptType();
      final PlayResult result = target.getResult();

      logger.debug("Entering: " + proc + "10");

      if (teamSeasonId == null) {
         errors.rejectValue("teamSeasonId", "play.teamSeasonId.required");
      }
      logger.debug(proc + "20");

      if (playerId == null) {
         errors.rejectValue("playerId", "shot.playerId.required");
      }
      logger.debug(proc + "30");

      if (type == null) {
         errors.rejectValue("attemptType", "shot.attemptType.required");
      }
      logger.debug(proc + "40");

      if (result == null) {
         errors.rejectValue("result", "shot.result.required");
      }
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that the faceoff winner is a valid player. If the player is registered as a game
    * attendee and is an athlete who is not inactive or injured, then processing continues.
    *
    * @param form
    * @param errors
    * @throws IllegalStateException if the player is not registered as a game attendee.
    */
   private void checkPlayer(ShotResource target, Errors errors) {
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

         final ShotEntry play = (ShotEntry)getPlayRepository().findOne(playId);
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

   /**
    * Validates that the score attempt type is valid. If the value is EMPTY_NET, OWN_GOAL,
    * PENALTY_SHOT or REGULAR, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkAttemptType(ShotResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkAttemptType.";
      final String playId = target.getPlayId();
      final ScoreAttemptType type = target.getAttemptType();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the
      // attempt type has changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final ShotEntry play = (ShotEntry)getPlayRepository().findOne(playId);
         if (!play.getScoreAttemptType().equals(type)) {
            logger.debug(proc + "40");
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation) {
         if (type == null ||
            (!type.equals(ScoreAttemptType.EMPTY_NET) && !type.equals(ScoreAttemptType.OWN_GOAL) &&
               !type.equals(ScoreAttemptType.PENALTY_SHOT) && !type.equals(ScoreAttemptType.REGULAR))) {
            errors.rejectValue("attemptType", "shot.attemptType.invalid");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the result is a valid result. If the result value is either GOAL, SHOT_BLOCKED,
    * SHOT_MISSED, SHOT_OFF_POST or SHOT_SAVED, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkResult(ShotResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkResult.";
      final String playId = target.getPlayId();
      final PlayResult result = target.getResult();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or the result has
      // changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");
         final ShotEntry clear = (ShotEntry)getPlayRepository().findOne(playId);
         if (!clear.getResult().equals(result)) {
            logger.debug(proc + "40");
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation) {
         if (result == null ||
            (!result.equals(PlayResult.GOAL) && !result.equals(PlayResult.SHOT_BLOCKED) &&
               !result.equals(PlayResult.SHOT_MISSED) && !result.equals(PlayResult.SHOT_OFF_POST) && !result
                  .equals(PlayResult.SHOT_SAVED))) {
            errors.rejectValue("result", "shot.result.invalid");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

}
