package laxstats.web.events;

import laxstats.api.events.PlayResult;
import laxstats.query.events.ClearEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class ClearFormValidator extends AbstractPlayValidator implements
		Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(ClearFormValidator.class);
	private static final String PACKAGE_NAME = ClearFormValidator.class
			.getPackage().getName();

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
		logger.debug(proc + "50");

		// Validate period
		checkPeriod(form, errors);
		logger.debug("Leaving: " + proc + "60");
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
	 * Validates that the team is a valid team. If the given team is
	 * participating in the event, then processing continues.
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
			final ClearEntry play = (ClearEntry) getPlayRepository().findOne(
					playId);
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
			final ClearEntry clear = (ClearEntry) getPlayRepository().findOne(
					playId);
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
			final ClearEntry clear = (ClearEntry) getPlayRepository().findOne(
					playId);
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
}
