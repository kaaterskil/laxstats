package laxstats.web.events;

import laxstats.api.events.PlayRole;
import laxstats.api.events.PlayUtils;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.query.events.AttendeeEntry;
import laxstats.query.events.FaceOffEntry;
import laxstats.query.events.GameEntry;
import laxstats.query.events.GameQueryRepository;
import laxstats.query.events.PlayParticipantEntry;

import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class FaceOffFormValidator extends AbstractPlayValidator implements
		Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(FaceOffFormValidator.class);
	private static final String PACKAGE_NAME = FaceOffFormValidator.class
			.getPackage().getName();

	@Autowired
	GameQueryRepository gameRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return FaceOffForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final String proc = PACKAGE_NAME + ".validate.";
		final FaceOffForm form = (FaceOffForm) target;

		logger.debug("Entering: " + proc + "10");

		// Validate mandatory args
		checkMandatoryArgs(form, errors);
		logger.debug(proc + "20");

		// Validate non-updateable args
		checkNonUpdateableArgs(form, errors);
		logger.debug(proc + "30");

		// Validate period
		checkPeriod(form, errors);
		logger.debug(proc + "40");

		// Validate elapsed time
		checkElapsedTime(form, errors);
		logger.debug(proc + "50");

		// Validate faceoff winner
		checkWinner(form, errors);
		logger.debug(proc + "60");

		// Validate faceoff loser
		checkLoser(form, errors);
		logger.debug("Leaving: " + proc + "70");
	}

	/**
	 * Validates that mandatory arguments have been set.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkMandatoryArgs(FaceOffForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

		logger.debug("Entering: " + proc + "10");

		ValidationUtils.rejectIfEmpty(errors, "teamSeasonId",
				"play.teamSeasonId.required");
		logger.debug(proc + "20");

		ValidationUtils.rejectIfEmpty(errors, "winnerId",
				"faceoff.winnerId.required");
		logger.debug(proc + "30");

		ValidationUtils.rejectIfEmpty(errors, "loserId",
				"faceoff.loserId.required");
		logger.debug(proc + "40");

		ValidationUtils.rejectIfEmpty(errors, "elapsedTime",
				"goal.elapsedTime.required");
		logger.debug("Leaving: " + proc + "50");
	}

	/**
	 * Validates that the period is valid. If the period is an integer >0 then
	 * processing continues.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkPeriod(FaceOffForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkPeriod.";
		final String playId = form.getPlayId();
		final int period = form.getPeriod();

		logger.debug("Entering: " + proc + "10");

		// Proceed with validation only if the record is new or the period has
		// changed.

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");
			final FaceOffEntry play = (FaceOffEntry) getPlayRepository()
					.findOne(playId);
			if (play.getPeriod() != period) {
				logger.debug(proc + "40");
				if (period < 0) {
					errors.rejectValue("period", "play.period.invalid");
				}
			}
		} else {
			logger.debug(proc + "50");
			if (period < 0) {
				errors.rejectValue("period", "play.period.invalid");
			}
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Validates that the elapsed time is a valid time. If the elapsed time is a
	 * positive value and <= the duration of the given period, then processing
	 * continues.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkElapsedTime(FaceOffForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkElapsedTime.";
		final String playId = form.getPlayId();
		final Period elapsedTime = form.getElapsedTime();
		final int period = form.getPeriod();
		boolean doValidation = false;

		logger.debug("Entering: " + proc + "10");

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");
			final FaceOffEntry play = (FaceOffEntry) getPlayRepository()
					.findOne(playId);
			if (!play.getElapsedTime().equals(elapsedTime)) {
				logger.debug(proc + "40");
				doValidation = true;
			}
		} else {
			logger.debug(proc + "50");
			doValidation = true;
		}

		if (doValidation) {
			final int elapsedSeconds = elapsedTime.toStandardSeconds()
					.getSeconds();
			if (elapsedSeconds < 0) {
				errors.rejectValue("elapsedTime", "play.elapsedTime.invalid");
			}
			logger.debug(proc + "70");

			if (period <= 4) {
				logger.debug(proc + "80");
				final int secondsInPeriod = PlayUtils.REGULAR_PERIOD_MINUTES
						.toStandardSeconds().getSeconds();
				if (elapsedSeconds > secondsInPeriod) {
					errors.rejectValue("elapsedTime",
							"play.elapsedTime.invalid");
				}
			} else {
				logger.debug(proc + "90");
				final int secondsInOvertime = PlayUtils.OVERTIME_PERIOD_MINUTES
						.toStandardSeconds().getSeconds();
				if (elapsedSeconds > secondsInOvertime) {
					errors.rejectValue("elapsedTime",
							"play.elapsedTime.invalid");
				}
			}

		}
		logger.debug("Leaving: " + proc + "100");
	}

	/**
	 * Validates that the faceoff winner is a valid player. If the player is
	 * registered as a game attendee and is an athlete who is not inactive or
	 * injured, then processing continues.
	 *
	 * @param form
	 * @param errors
	 * @throws IllegalStateException if the player is not registered as a game
	 *             attendee.
	 */
	private void checkWinner(FaceOffForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkWinner.";
		final String playId = form.getPlayId();
		final String gameId = form.getGameId();
		final String attendeeId = form.getWinnerId();
		boolean doValidation = false;

		logger.debug("Entering: " + proc + "10");

		// Only proceed with validation if the record is new or if the player
		// has changed.

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final FaceOffEntry play = (FaceOffEntry) getPlayRepository()
					.findOne(playId);
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
		} else {
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
				errors.rejectValue("winnerId",
						"faceoff.winnerId.inactivePlayer");
			}
			logger.debug(proc + "90");

			if (attendee.getPlayer().getStatus().equals(PlayerStatus.INJURED)) {
				errors.rejectValue("winnerId", "faceoff.winnerId.injuredPlayer");
			}
		}
		logger.debug("Leaving: " + proc + "100");
	}

	/**
	 * Validates that the faceoff loser is a valid player. If the player is
	 * registered as a game attendee and is an athlete who is not inactive or
	 * injured, then processing continues.
	 *
	 * @param form
	 * @param errors
	 * @throws IllegalStateException if the player is not registered as a game
	 *             attendee.
	 */
	private void checkLoser(FaceOffForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkWinner.";
		final String playId = form.getPlayId();
		final String gameId = form.getGameId();
		final String attendeeId = form.getLoserId();
		boolean doValidation = false;

		logger.debug("Entering: " + proc + "10");

		// Only proceed with validation if the record is new or if the player
		// has changed.

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final FaceOffEntry play = (FaceOffEntry) getPlayRepository()
					.findOne(playId);
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
		} else {
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
