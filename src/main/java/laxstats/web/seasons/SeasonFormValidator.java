package laxstats.web.seasons;

import laxstats.api.Common;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class SeasonFormValidator implements Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(SeasonFormValidator.class);
	private static final String PACKAGE_NAME = SeasonFormValidator.class
			.getPackage().getName();

	@Autowired
	private SeasonQueryRepository seasonRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return SeasonForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final String proc = PACKAGE_NAME + ".validate.";
		final SeasonForm form = (SeasonForm) target;

		logger.debug("Entering: " + proc + "10");

		// Validate mandatory arguments
		checkMandatoryArgs(form, errors);
		logger.debug(proc + "20");

		// Validate description
		checkDescription(form, errors);
		logger.debug(proc + "30");

		// Validate endsOn
		checkEndDate(form, errors);
		logger.debug(proc + "40");

		// Validate date overlap with other seasons
		checkDates(form, errors);

		logger.debug("Leaving: " + proc + "50");
	}

	/**
	 * Validates that mandatory arguments have been set
	 *
	 * @param form
	 * @param errors
	 */
	private void checkMandatoryArgs(SeasonForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

		logger.debug("Entering: " + proc + "10");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"season.description.required");
		logger.debug(proc + "20");

		ValidationUtils.rejectIfEmpty(errors, "startsOn",
				"season.startsOn.required");

		logger.debug("Leaving: " + proc + "30");
	}

	/**
	 * Validates that <code>description</code> is unique.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkDescription(SeasonForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkDescription.";
		final String seasonId = form.getId();
		final String description = form.getDescription();
		int found = 0;

		logger.debug("Entering: " + proc + "10");

		final boolean isUpdating = apiUpdating(seasonId);
		logger.debug(proc + "20");

		if (isUpdating) {
			final SeasonEntry season = seasonRepository.findOne(seasonId);
			logger.debug(proc + "30");

			if ((description != null && season.getDescription() == null)
					|| !season.getDescription().equals(description)) {
				logger.debug(proc + "40");

				found = seasonRepository.updateDescription(description,
						seasonId);
				if (found > 0) {
					errors.rejectValue("description",
							"season.description.duplicate");
				}
			}
		} else if (!isUpdating) {
			logger.debug(proc + "50");

			found = seasonRepository.uniqueDescription(description);
			if (found > 0) {
				errors.rejectValue("description",
						"season.description.duplicate");
			}
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Validates that <code>endsOn</code>, when set, is less than or equal to
	 * the value for <code>startsOn</code> on the same Season record.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkEndDate(SeasonForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkEndDate.";
		final String seasonId = form.getId();
		final LocalDate startsOn = form.getStartsOn();
		final LocalDate endsOn = form.getEndsOn();
		final LocalDate eot = Common.EOT.toLocalDate();

		logger.debug("Entering: " + proc + "10");

		final boolean isUpdating = apiUpdating(seasonId);
		logger.debug(proc + "20");

		boolean doValidation = false;
		if (isUpdating) {
			final SeasonEntry season = seasonRepository.findOne(seasonId);
			logger.debug(proc + "30");

			// Test if either the startsOn or endsOn dates have changed
			if (!Common.nvl(season.getEndsOn(), eot).equals(
					Common.nvl(endsOn, eot))
					|| !season.getStartsOn().equals(startsOn)) {
				logger.debug(proc + "40");
				doValidation = true;
			}
		} else {
			logger.debug(proc + "50");
			doValidation = true;
		}

		if (doValidation && startsOn.isAfter(Common.nvl(endsOn, eot))) {
			errors.rejectValue("endsOn", "season.endsOn.beforeStart");
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Validates that <code>startsOn</code> and <code>endsOn</code> for the
	 * given record are unique and do not overlap with other records.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkDates(SeasonForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkDates.";
		final String seasonId = form.getId();
		final LocalDate startsOn = form.getStartsOn();
		final LocalDate endsOn = Common.nvl(form.getEndsOn(),
				Common.EOT.toLocalDate());

		logger.debug("Entering: " + proc + "10");

		final boolean isUpdating = apiUpdating(seasonId);
		logger.debug(proc + "20");

		final Iterable<SeasonEntry> list = seasonRepository.findAll();
		logger.debug(proc + "30");

		if (isUpdating) {
			logger.debug(proc + "40");
			for (final SeasonEntry each : list) {
				if (!each.getId().equals(seasonId)
						&& each.overlaps(startsOn, endsOn)) {
					errors.rejectValue("endsOn", "season.overlap");
					break;
				}
			}
		} else {
			logger.debug(proc + "50");
			for (final SeasonEntry each : list) {
				if (each.overlaps(startsOn, endsOn)) {
					errors.rejectValue("endsOn", "season.overlap");
					break;
				}
			}
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Returns <code>true</code> if the record with the given primary key is
	 * being updated, <code>false</code> otherwise.
	 *
	 * @param seasonId
	 * @return boolean
	 * @throws IllegalArgumentException if no record exists for the given
	 *             primary key
	 */
	private boolean apiUpdating(String seasonId) {
		boolean result = false;
		if (seasonId == null) {
			// The primary key is null
			result = false;
		} else {
			final boolean found = seasonRepository.exists(seasonId);
			if (!found) {
				// The primary key is invalid
				throw new IllegalArgumentException("Invalid primary key");
			}
			result = true;
		}
		return result;
	}
}
