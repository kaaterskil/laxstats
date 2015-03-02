package laxstats.web.events;

import laxstats.api.events.PlayResult;
import laxstats.query.events.ClearEntry;
import laxstats.query.events.PlayQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ClearFormValidator implements Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(ClearFormValidator.class);
	private static final String PACKAGE_NAME = ClearFormValidator.class
			.getPackage().getName();

	@Autowired
	PlayQueryRepository playRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return ClearForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final String proc = PACKAGE_NAME + ".validate.";
		final ClearForm form = (ClearForm) target;

		logger.debug("Entering: " + proc + "10");

		// Validate mandatory args
		checkMandatoryArgs(form, errors);
		logger.debug(proc + "20");

		// Validate non-updateable args
		checkNonUpdateableArgs(form, errors);
		logger.debug(proc + "30");

		// Validate team
		checkTeam(form, errors);
		logger.debug(proc + "40");

		// Validate result
		checkResult(form, errors);
		logger.debug(proc + "40");

		// Validate period
		checkPeriod(form, errors);
		logger.debug("Leaving: " + proc + "50");
	}

	/**
	 * Validates that mandatory arguments have been set.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkMandatoryArgs(ClearForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryargs.";

		logger.debug("Entering: " + proc + "10");

		ValidationUtils.rejectIfEmpty(errors, "teamSeasonId",
				"play.teamSeasonId.required");
		logger.debug(proc + "20");

		ValidationUtils
				.rejectIfEmpty(errors, "result", "clear.result.required");
		logger.debug("Leaving: " + proc + "30");
	}

	/**
	 * Validates that non-updateable arguments have not changed.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkNonUpdateableArgs(ClearForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkNonUpdateableArgs.";
		final String playId = form.getPlayId();
		final String eventId = form.getGameId();

		logger.debug("Entering: " + proc + "10");

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final ClearEntry play = (ClearEntry) playRepository.findOne(playId);
			if (play.getEvent().getId().equals(eventId)) {
				throw new IllegalStateException("Invalid game identifier");
			}
		}
		logger.debug("Leaving: " + proc + "40");
	}

	/**
	 * Validates that the team is a valid team. If the given team is
	 * partiipating in the event, then processing continues.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkTeam(ClearForm form, Errors errors) {
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

			final ClearEntry clear = (ClearEntry) playRepository
					.findOne(playId);
			if (!clear.getTeam().getId().equals(teamSeasonId)) {
				logger.debug(proc + "40");

				exists = playRepository.teamSeasonExists(gameId, teamSeasonId);
				if (!exists) {
					throw new IllegalStateException("Invalid team season");
				}
			}
		} else {
			logger.debug(proc + "50");

			exists = playRepository.teamSeasonExists(gameId, teamSeasonId);
			if (!exists) {
				throw new IllegalStateException("Invalid team season");
			}
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Validates that the result is a valid result. If the result value is
	 * either CLEAR_SUCCEEDED or CLEAR_FAILED, then processing continues.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkResult(ClearForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryargs.";
		final String playId = form.getPlayId();
		final PlayResult result = form.getResult();

		logger.debug("Entering: " + proc + "10");

		// Proceed with validation only if the record is new or the result has
		// changed.

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final ClearEntry clear = (ClearEntry) playRepository
					.findOne(playId);
			if (!clear.getResult().equals(result)) {
				logger.debug(proc + "40");
				if (!result.equals(PlayResult.CLEAR_FAILED)
						&& !result.equals(PlayResult.CLEAR_SUCCEEDED)) {
					errors.rejectValue("result", "clear.result.invalid");
				}
			}
		} else {
			logger.debug(proc + "50");
			if (!result.equals(PlayResult.CLEAR_FAILED)
					&& !result.equals(PlayResult.CLEAR_SUCCEEDED)) {
				errors.rejectValue("result", "clear.result.invalid");
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
	private void checkPeriod(ClearForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryargs.";
		final String playId = form.getPlayId();
		final int period = form.getPeriod();

		logger.debug("Entering: " + proc + "10");

		// Proceed with validation only if the record is new or the period has
		// changed.

		final boolean isUpdating = apiUpdating(playId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final ClearEntry clear = (ClearEntry) playRepository
					.findOne(playId);
			if (clear.getPeriod() != period) {
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
	 * Returns true if the record with the given primary key is being
	 * updated,false otherwise.
	 *
	 * @param playId
	 * @return
	 * @throws IllegalStateException if no record exists with the given primary
	 *             key.
	 */
	private boolean apiUpdating(String playId) {
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
