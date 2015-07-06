package laxstats.web.games;

import laxstats.api.games.PlayRole;
import laxstats.api.games.ScoreAttemptType;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.api.utils.Constants;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.GoalEntry;
import laxstats.query.games.PlayParticipantEntry;

import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class GoalValidator extends AbstractPlayValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(GoalValidator.class);
   private static final String PACKAGE_NAME = GoalValidator.class.getPackage().getName();

   @Autowired
   GameQueryRepository gameRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return GoalResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final GoalResource resource = (GoalResource)target;

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

      // Validate elapsed time
      checkElapsedTime(resource, errors);
      logger.debug(proc + "50");

      // Validate scorer
      checkScorer(resource, errors);
      logger.debug(proc + "60");

      // Validate assist
      checkAssist(resource, errors);
      logger.debug(proc + "60");

      // Validate attempt type
      checkAttemptType(resource, errors);
      logger.debug("Leaving: " + proc + "80");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(GoalResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String teamSeasonId = target.getTeamSeasonId();
      final String scorerId = target.getScorerId();
      final String elapsedTime = target.getElapsedTime();
      final ScoreAttemptType type = target.getAttemptType();

      logger.debug("Entering: " + proc + "10");

      if (teamSeasonId == null) {
         errors.rejectValue("teamSeasonId", "play.teamSeasonId.required");
      }
      logger.debug(proc + "20");

      if (scorerId == null) {
         errors.rejectValue("scorerId", "goal.scorerId.required");
      }
      logger.debug(proc + "30");

      if (elapsedTime == null) {
         errors.rejectValue("elapsedTime", "goal.elapsedTime.required");
      }
      logger.debug(proc + "40");

      if (type == null) {
         errors.rejectValue("attemptType", "goal.attemptType.required");
      }
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that the elapsed time is a valid time. If the elapsed time is a positive value and
    * <= the duration of the given period, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkElapsedTime(GoalResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkElapsedTime.";
      final String playId = target.getPlayId();
      final Period elapsedTime = target.getElapsedTimeAsPeriod();
      final int period = target.getPeriod();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");
         final GoalEntry play = (GoalEntry)getPlayRepository().findOne(playId);
         if (!play.getElapsedTime().equals(elapsedTime)) {
            logger.debug(proc + "40");
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation) {
         final int elapsedSeconds = elapsedTime.toStandardSeconds().getSeconds();
         if (elapsedSeconds < 0) {
            errors.rejectValue("elapsedTime", "play.elapsedTime.invalid");
         }
         logger.debug(proc + "70");

         if (period <= 4) {
            logger.debug(proc + "80");
            final int secondsInPeriod =
               Constants.REGULAR_PERIOD_MINUTES.toStandardSeconds().getSeconds();
            if (elapsedSeconds > secondsInPeriod) {
               errors.rejectValue("elapsedTime", "play.elapsedTime.invalid");
            }
         }
         else {
            logger.debug(proc + "90");
            final int secondsInOvertime =
               Constants.OVERTIME_PERIOD_MINUTES.toStandardSeconds().getSeconds();
            if (elapsedSeconds > secondsInOvertime) {
               errors.rejectValue("elapsedTime", "play.elapsedTime.invalid");
            }
         }

      }
      logger.debug("Leaving: " + proc + "100");
   }

   /**
    * Validates that the scorer is a valid player. If the player is registered as a game attendee
    * and is an athlete who is not inactive or injured, then processing continues.
    *
    * @param form
    * @param errors
    * @throws IllegalStateException if the player is not registered as a game attendee.
    */
   private void checkScorer(GoalResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkScorer.";
      final String playId = target.getPlayId();
      final String gameId = target.getGameId();
      final String attendeeId = target.getScorerId();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the player
      // has changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final GoalEntry play = (GoalEntry)getPlayRepository().findOne(playId);
         PlayParticipantEntry oldAttendee = null;
         for (final PlayParticipantEntry each : play.getParticipants()) {
            if (each.getRole().equals(PlayRole.SCORER)) {
               oldAttendee = each;
               break;
            }
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
            throw new IllegalStateException("Invalid scorer");
         }
         logger.debug(proc + "70");

         if (!attendee.getPlayer().getRole().equals(Role.ATHLETE)) {
            errors.rejectValue("scorerId", "goal.scorer.invalidPlayer");
         }
         logger.debug(proc + "80");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INACTIVE)) {
            errors.rejectValue("scorerId", "goal.scorer.inactivePlayer");
         }
         logger.debug(proc + "90");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INJURED)) {
            errors.rejectValue("scorerId", "goal.scorer.injuredPlayer");
         }
      }
      logger.debug("Leaving: " + proc + "100");
   }

   /**
    * Validates that the assist, if any, is a valid player. If the player is registered as a game
    * attendee and is an athlete who is not inactive or injured, then processing continues.
    *
    * @param form
    * @param errors
    * @throws IllegalStateException if the player is not registered as a game attendee.
    */
   private void checkAssist(GoalResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkAssist.";
      final String playId = target.getPlayId();
      final String gameId = target.getGameId();
      final String attendeeId = target.getAssistId();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      if (attendeeId != null) {
         logger.debug(proc + "20");

         // Only proceed with validation if the record is new or if the
         // player has changed.

         final boolean isUpdating = apiUpdating(playId);
         logger.debug(proc + "30");

         if (isUpdating) {
            logger.debug(proc + "40");

            final GoalEntry play = (GoalEntry)getPlayRepository().findOne(playId);
            PlayParticipantEntry oldAttendee = null;
            for (final PlayParticipantEntry each : play.getParticipants()) {
               if (each.getRole().equals(PlayRole.ASSIST)) {
                  oldAttendee = each;
                  break;
               }
            }
            logger.debug(proc + "50");

            if (oldAttendee == null || !oldAttendee.getId().equals(attendeeId)) {
               doValidation = true;
            }
         }
         else {
            logger.debug(proc + "60");
            doValidation = true;
         }

         if (doValidation) {
            final GameEntry game = gameRepository.findOne(gameId);
            logger.debug(proc + "70");

            final AttendeeEntry attendee = game.getAttendee(attendeeId);
            if (attendee == null) {
               throw new IllegalStateException("Invalid assist");
            }
            logger.debug(proc + "80");

            if (!attendee.getPlayer().getRole().equals(Role.ATHLETE)) {
               errors.rejectValue("assistId", "goal.assist.invalidPlayer");
            }
            logger.debug(proc + "90");

            if (attendee.getPlayer().getStatus().equals(PlayerStatus.INACTIVE)) {
               errors.rejectValue("assistId", "goal.assist.inactivePlayer");
            }
            logger.debug(proc + "100");

            if (attendee.getPlayer().getStatus().equals(PlayerStatus.INJURED)) {
               errors.rejectValue("assistId", "goal.assist.injuredPlayer");
            }
         }
      }
      logger.debug("Leaving: " + proc + "110");
   }

   /**
    * Validates that the score attempt type is valid. If the value is EMPTY_NET, OWN_GOAL,
    * PENALTY_SHOT or REGULAR, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkAttemptType(GoalResource target, Errors errors) {
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

         final GoalEntry play = (GoalEntry)getPlayRepository().findOne(playId);
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
            errors.rejectValue("attemptType", "goal.attemptType.invalid");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }
}
