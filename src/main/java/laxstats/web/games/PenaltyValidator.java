package laxstats.web.games;

import laxstats.api.games.PlayRole;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.api.utils.Common;
import laxstats.api.utils.Constants;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.PenaltyEntry;
import laxstats.query.games.PlayParticipantEntry;
import laxstats.query.violations.ViolationQueryRepository;

import org.joda.time.Period;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class PenaltyValidator extends AbstractPlayValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(PenaltyValidator.class);
   private static final String PACKAGE_NAME = PenaltyValidator.class.getPackage().getName();

   @Autowired
   private GameQueryRepository gameRepository;

   @Autowired
   private ViolationQueryRepository violationRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return PenaltyResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final PenaltyResource resource = (PenaltyResource)target;

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

      // Validate team
      checkTeam(resource, errors);
      logger.debug(proc + "60");

      // Validate committed by player
      checkCommittedBy(resource, errors);
      logger.debug(proc + "70");

      // Validate committed against player
      checkCommittedAgainst(resource, errors);
      logger.debug(proc + "80");

      // Validate violation
      checkViolation(resource, errors);
      logger.debug(proc + "90");

      // Validate penalty duration
      checkDuration(resource, errors);
      logger.debug("Leaving " + proc + "90");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(PenaltyResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String teamSeasonId = target.getTeamSeasonId();
      final String committedById = target.getCommittedById();
      final String elapsedTime = target.getElapsedTime();
      final String violationId = target.getViolationId();
      final String duration = target.getDuration();

      logger.debug("Entering: " + proc + "10");

      if (teamSeasonId == null) {
         errors.rejectValue("teamSeasonId", "play.teamSeasonId.required");
      }
      logger.debug(proc + "20");

      if (committedById == null) {
         errors.rejectValue("committedById", "penalty.committedById.required");
      }
      logger.debug(proc + "30");

      if (elapsedTime == null) {
         errors.rejectValue("elapsedTime", "play.elapsedTime.required");
      }
      logger.debug(proc + "40");

      if (violationId == null) {
         errors.rejectValue("violationId", "penalty.violationId.required");
      }
      logger.debug(proc + "50");

      if (duration == null) {
         errors.rejectValue("duration", "penalty.duration.required");
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the elapsed time is a valid time. If the elapsed time is a positive value and
    * <= the duration of the given period, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkElapsedTime(PenaltyResource target, Errors errors) {
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
         final PenaltyEntry play = (PenaltyEntry)getPlayRepository().findOne(playId);
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
   private void checkCommittedBy(PenaltyResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkWinner.";
      final String playId = target.getPlayId();
      final String gameId = target.getGameId();
      final String attendeeId = target.getCommittedById();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the player
      // has changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final PenaltyEntry play = (PenaltyEntry)getPlayRepository().findOne(playId);
         PlayParticipantEntry oldAttendee = null;
         for (final PlayParticipantEntry each : play.getParticipants()) {
            if (each.getRole().equals(PlayRole.PENALTY_COMMITTED_BY)) {
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
            errors.rejectValue("committedBy", "penalty.committedBy.invalidPlayer");
         }
         logger.debug(proc + "80");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INACTIVE)) {
            errors.rejectValue("committedBy", "penalty.committedBy.inactivePlayer");
         }
         logger.debug(proc + "90");

         if (attendee.getPlayer().getStatus().equals(PlayerStatus.INJURED)) {
            errors.rejectValue("committedBy", "penalty.committedBy.injuredPlayer");
         }
      }
      logger.debug("Leaving: " + proc + "100");
   }

   /**
    * Validates that the player committed against, if any, is a valid player. If the player is
    * registered as a game attendee and is an athlete who is not inactive or injured, then
    * processing continues.
    *
    * @param form
    * @param errors
    * @throws IllegalStateException if the player is not registered as a game attendee.
    */
   private void checkCommittedAgainst(PenaltyResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkAssist.";
      final String playId = target.getPlayId();
      final String gameId = target.getGameId();
      final String attendeeId = target.getCommittedAgainstId();
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

            final PenaltyEntry play = (PenaltyEntry)getPlayRepository().findOne(playId);
            PlayParticipantEntry oldAttendee = null;
            for (final PlayParticipantEntry each : play.getParticipants()) {
               if (each.getRole().equals(PlayRole.PENALTY_COMMITTED_AGAINST)) {
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
               throw new IllegalStateException("Invalid player");
            }
            logger.debug(proc + "80");

            if (!attendee.getPlayer().getRole().equals(Role.ATHLETE)) {
               errors.rejectValue("committedAgainst", "penalty.committedAgainst.invalidPlayer");
            }
            logger.debug(proc + "90");

            if (attendee.getPlayer().getStatus().equals(PlayerStatus.INACTIVE)) {
               errors.rejectValue("committedAgainst", "penalty.committedAgainst.inactivePlayer");
            }
            logger.debug(proc + "100");

            if (attendee.getPlayer().getStatus().equals(PlayerStatus.INJURED)) {
               errors.rejectValue("committedAgainst", "penalty.committedAgainst.injuredPlayer");
            }
         }
      }
      logger.debug("Leaving: " + proc + "110");
   }

   /**
    * Validates that the violation type is valid. If the value is a valid penalty type, then
    * processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkViolation(PenaltyResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkAttemptType.";
      final String playId = target.getPlayId();
      final String violationId = target.getViolationId();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the
      // attempt type has changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final PenaltyEntry play = (PenaltyEntry)getPlayRepository().findOne(playId);
         if (!play.getViolation().getId().equals(violationId)) {
            logger.debug(proc + "40");
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation) {
         if (violationId == null) {
            errors.rejectValue("attemptType", "penalty.violation.required");
         }

         final boolean exists = violationRepository.exists(violationId);
         if (!exists) {
            errors.rejectValue("attemptType", "penalty.violation.invalid");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the penalty duration is valid. If the duration in seconds is a positive
    * integer, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkDuration(PenaltyResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkAttemptType.";
      final String playId = target.getPlayId();
      final Period duration = target.getDurationAsPeriod();
      boolean doValidation = false;
      final Period maxTime = Period.seconds(Seconds.MAX_VALUE.getSeconds());

      logger.debug("Entering: " + proc + "10");

      // Only proceed with validation if the record is new or if the
      // attempt type has changed.

      final boolean isUpdating = apiUpdating(playId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final PenaltyEntry play = (PenaltyEntry)getPlayRepository().findOne(playId);
         if (!Common.nvl(play.getDuration(), maxTime).equals(Common.nvl(duration, maxTime))) {
            logger.debug(proc + "40");
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation) {
         if (duration == null) {
            errors.rejectValue("duration", "penalty.duration.required");
         }
         logger.debug(proc + "60");

         final int seconds = duration.toStandardSeconds().getSeconds();
         if (seconds < 0) {
            errors.rejectValue("duration", "penalty.duration.invalid");
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }
}
