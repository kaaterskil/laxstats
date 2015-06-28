package laxstats.web.games;

import laxstats.api.games.PlayKey;
import laxstats.query.games.PlayEntry;
import laxstats.query.games.PlayQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public abstract class AbstractPlayValidator {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractPlayValidator.class);
	private static final String PACKAGE_NAME = AbstractPlayValidator.class
			.getPackage().getName();

	@Autowired
	private PlayQueryRepository playRepository;

	protected AbstractPlayValidator() {
	}

	public PlayQueryRepository getPlayRepository() {
		return playRepository;
	}

	/**
	 * Validates that non-updateable arguments have not changed.
	 *
	 * @param form
	 * @param errors
	 */
	protected void checkNonUpdateableArgs(AbstractPlayForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkNonUpdateableArgs.";
		final String playId = form.getPlayId();
		final String playType = form.getPlayType();
		final PlayKey playKey = form.getPlayKey();
		final String eventId = form.getGameId();

		logger.debug("Entering: " + proc + "10");

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final PlayEntry play = playRepository.findOne(playId);
			if (!play.getPlayType().equals(playType)) {
				throw new IllegalStateException("Invalid play type");
			}
			logger.debug(proc + "40");

			if (!play.getPlayKey().equals(playKey)) {
				throw new IllegalStateException("Invalid play key");
			}
			logger.debug(proc + "50");

			if (play.getEvent().getId().equals(eventId)) {
				throw new IllegalStateException("Invalid game identifier");
			}
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Validates that the team is a valid team. If the given team is
	 * participating in the event, then processing continues.
	 *
	 * @param form
	 * @param errors
	 */
	protected void checkTeam(AbstractPlayForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkTeam.";
		final String playId = form.getPlayId();
		final String teamSeasonId = form.getTeamSeasonId();
		final String gameId = form.getGameId();
		boolean exists = false;

		logger.debug("Entering: " + proc + "10");

		// Proceed with validation only if the record is new or the team has
		// changed.

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");
			final PlayEntry play = getPlayRepository().findOne(playId);
			if (!play.getTeam().getId().equals(teamSeasonId)) {
				logger.debug(proc + "40");
				exists = getPlayRepository().teamSeasonExists(gameId,
						teamSeasonId);
				if (!exists) {
					throw new IllegalStateException("Invalid team season");
				}
			}
		} else {
			logger.debug(proc + "50");
			exists = getPlayRepository().teamSeasonExists(gameId, teamSeasonId);
			if (!exists) {
				throw new IllegalStateException("Invalid team season");
			}
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Validates that the period is valid. If the period is an integer >0 then
	 * processing continues.
	 *
	 * @param form
	 * @param errors
	 */
	protected void checkPeriod(AbstractPlayForm form, Errors errors) {
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
			final PlayEntry play = getPlayRepository().findOne(playId);
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
	 * Returns true if the record with the given primary key is being updated,
	 * false otherwise.
	 *
	 * @param playId
	 * @return
	 * @throws IllegalStateException if no record exists with the given primary
	 *             key.
	 */
	protected boolean apiUpdating(String playId) {
		boolean result = false;
		if (playId == null) {
			result = false;
		} else {
			final boolean exists = playRepository.exists(playId);
			if (!exists) {
				throw new IllegalStateException("Invalid primary key");
			}
			result = true;
		}
		return result;
	}
}
