package laxstats.web.events;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.query.events.AttendeeEntry;
import laxstats.query.events.GameEntry;
import laxstats.query.events.GameQueryRepository;
import laxstats.query.events.GroundBallEntry;
import laxstats.query.events.PlayParticipantEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class GroundBallFormValidator extends AbstractPlayValidator implements
		Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(GroundBallFormValidator.class);
	private static final String PACKAGE_NAME = GroundBallFormValidator.class
			.getPackage().getName();

	@Autowired
	GameQueryRepository gameRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return GroundBallForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final String proc = PACKAGE_NAME + ".validate.";
		final GroundBallForm form = (GroundBallForm) target;

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

		// Validate team
		checkTeam(form, errors);
		logger.debug(proc + "50");

		// Validate player
		checkPlayer(form, errors);
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Validates that mandatory arguments have been set.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkMandatoryArgs(GroundBallForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

		logger.debug("Entering: " + proc + "10");

		ValidationUtils.rejectIfEmpty(errors, "teamSeasonId",
				"play.teamSeasonId.required");
		logger.debug(proc + "20");

		ValidationUtils.rejectIfEmpty(errors, "playerId",
				"groundBall.playerId.required");
		logger.debug("Leaving: " + proc + "30");
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
	private void checkPlayer(GroundBallForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkPlayer.";
		final String playId = form.getPlayId();
		final String gameId = form.getGameId();
		final String attendeeId = form.getPlayerId();
		boolean doValidation = false;

		logger.debug("Entering: " + proc + "10");

		// Only proceed with validation if the record is new or if the player
		// has changed.

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final GroundBallEntry play = (GroundBallEntry) getPlayRepository()
					.findOne(playId);
			PlayParticipantEntry oldAttendee = null;
			if (!play.getParticipants().isEmpty()) {
				oldAttendee = play.getParticipants().get(0);
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
