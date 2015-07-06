package laxstats.web.games;

import laxstats.api.games.PlayRole;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.api.utils.Constants;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.FaceOffEntry;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.PlayParticipantEntry;

import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class FaceOffValidator extends AbstractPlayValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(FaceOffValidator.class);
   private static final String PACKAGE_NAME = FaceOffValidator.class.getPackage().getName();

   @Autowired
   GameQueryRepository gameRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return FaceOffResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final FaceOffResource resource = (FaceOffResource)target;

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

      // Validate faceoff winner
      checkWinner(resource, errors);
      logger.debug(proc + "60");

      // Validate faceoff loser
      checkLoser(resource, errors);
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(FaceOffResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String teamSeasonId = target.getTeamSeasonId();
      final String winnerId = target.getWinnerId();
      final String loserId = target.getLoserId();
      final String elapsedTime = target.getElapsedTime();

      logger.debug("Entering: " + proc + "10");

      if (teamSeasonId == null) {
         errors.rejectValue("teamSeasonId", "play.teamSeasonId.required");
      }
      logger.debug(proc + "20");

      if (winnerId == null) {
         errors.rejectValue("winnerId", "faceoff.winnerId.required");
      }
      logger.debug(proc + "30");

      if (loserId == null) {
         errors.rejectValue("loserId", "faceoff.loserId.required");
      }
      logger.debug(proc + "40");

      if (elapsedTime == null) {
         errors.rejectValue("elapsedTime", "play.elapsedTime.required");
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
   private void checkElapsedTime(FaceOffResource target, Errors errors) {
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
         final FaceOffEntry play = (FaceOffEntry)getPlayRepository().findOne(playId);
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
    * Validates that the faceoff winner is a valid player. If the player is registered as a game
    * attendee and is an athlete who is not inactive or injured, then processing continues.
    *
    * @param form
    * @param errors
    * @throws IllegalStateException if the player is not registered as a game attendee.
    */
   private void checkWinner(FaceOffResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkWinner.";
      final String playId = target.getPlayId();
      final String gameId = target.getGameId();
      final String attendeeId = target.getWinnerId();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the player
      // has changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final FaceOffEntry play = (FaceOffEntry)getPlayRepository().findOne(playId);
         PlayParticipantEntry oldAttendee = null;
         for (final PlayParticipantEntry each : play.getParticipants()) {
            if (each.getRole().equals(PlayRole.FACEOFF_WINNER)) {
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
            throw new IllegalStateException("Invalid faceoff winner");
         }
         logger.debug(proc + "70");

         if (!attendee.getPlayer().getRole().equals(Role.ATHLETE)) {
            errors.rejectValue("winnerId", "faceoff.winnerId.invalidPlayer");
         }
         logger.debug(proc + "80");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INACTIVE)) {
            errors.rejectValue("winnerId", "faceoff.winnerId.inactivePlayer");
         }
         logger.debug(proc + "90");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INJURED)) {
            errors.rejectValue("winnerId", "faceoff.winnerId.injuredPlayer");
         }
      }
      logger.debug("Leaving: " + proc + "100");
   }

   /**
    * Validates that the faceoff loser is a valid player. If the player is registered as a game
    * attendee and is an athlete who is not inactive or injured, then processing continues.
    *
    * @param form
    * @param errors
    * @throws IllegalStateException if the player is not registered as a game attendee.
    */
   private void checkLoser(FaceOffResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkWinner.";
      final String playId = target.getPlayId();
      final String gameId = target.getGameId();
      final String attendeeId = target.getLoserId();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the player
      // has changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final FaceOffEntry play = (FaceOffEntry)getPlayRepository().findOne(playId);
         PlayParticipantEntry oldAttendee = null;
         for (final PlayParticipantEntry each : play.getParticipants()) {
            if (each.getRole().equals(PlayRole.FACEOFF_LOSER)) {
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
            throw new IllegalStateException("Invalid faceoff loser");
         }
         logger.debug(proc + "70");

         if (!attendee.getPlayer().getRole().equals(Role.ATHLETE)) {
            errors.rejectValue("loserId", "faceoff.loserId.invalidPlayer");
         }
         logger.debug(proc + "80");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INACTIVE)) {
            errors.rejectValue("winnerId", "faceoff.loserId.inactivePlayer");
         }
         logger.debug(proc + "90");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INJURED)) {
            errors.rejectValue("winnerId", "faceoff.loserId.injuredPlayer");
         }
      }
      logger.debug("Leaving: " + proc + "80");
   }
}
