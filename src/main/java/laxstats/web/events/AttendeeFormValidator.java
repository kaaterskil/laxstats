package laxstats.web.events;

import laxstats.api.players.Role;
import laxstats.query.events.AttendeeEntry;
import laxstats.query.events.AttendeeQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class AttendeeFormValidator implements Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(AttendeeFormValidator.class);
	private static final String PACKAGE_NAME = AttendeeFormValidator.class
			.getPackage().getName();

	@Autowired
	private AttendeeQueryRepository attendeeRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return AttendeeForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final String proc = PACKAGE_NAME + ".validate.";
		final AttendeeForm form = (AttendeeForm) target;

		logger.debug("Entering: " + proc + "10");

		// Validate mandatory args
		checkMandatoryArgs(form, errors);
		logger.debug(proc + "20");

		// Validate non-updateable args
		checkNonUpdateableArgs(form, errors);
		logger.debug("Leaving: " + proc + "30");
	}

	/**
	 * Validates that mandatory arguments have been set.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkMandatoryArgs(AttendeeForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

		logger.debug("Entering: " + proc + "10");

		ValidationUtils.rejectIfEmpty(errors, "role", "attendee.role.required");
		logger.debug(proc + "20");

		ValidationUtils.rejectIfEmpty(errors, "playerId",
				"attendee.player.required");
		logger.debug(proc + "30");

		if (form.getRole().equals(Role.ATHLETE)) {
			logger.debug(proc + "40");
			ValidationUtils.rejectIfEmpty(errors, "status",
					"attendee.status.required");
		}
		logger.debug("Leaving: " + proc + "50");
	}

	/**
	 * Validates non-updateable arguments. If the given player has not changed,
	 * then processing continues.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkNonUpdateableArgs(AttendeeForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
		final String attendeeId = form.getId();
		final String playerId = form.getPlayerId();

		logger.debug("Entering: " + proc + "10");

		final boolean isUpdating = apiUpdating(attendeeId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final AttendeeEntry oldAttendee = attendeeRepository
					.findOne(attendeeId);
			if (!oldAttendee.getPlayer().getId().equals(playerId)) {
				errors.rejectValue("playerId", "attendee.player.nonUpdateable");
			}
		}
		logger.debug("Leaving: " + proc + "40");
	}

	/**
	 * Returns true if the record with the given primary key is being updated,
	 * false otherwise.
	 *
	 * @param attendeeId
	 * @return
	 * @throws IllegalStateException if no record exists with the given primary
	 *             key.
	 */
	private boolean apiUpdating(String attendeeId) {
		boolean result = false;
		if (attendeeId == null) {
			result = false;
		} else {
			final boolean exists = attendeeRepository.exists(attendeeId);
			if (!exists) {
				throw new IllegalStateException("Invalid primary key");
			}
			result = true;
		}
		return result;
	}
}
