package laxstats.web.users;

import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class UserFormValidator implements Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(UserFormValidator.class);
	private static final String PACKAGE_NAME = UserFormValidator.class
			.getPackage().getName();

	@Autowired
	private UserQueryRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final String proc = PACKAGE_NAME + ".validate.";
		final UserForm form = (UserForm) target;

		logger.debug("Entering: " + proc + "10");

		// Validate mandatory arguments
		checkMandatoryArgs(form, errors);
		logger.debug(proc + "20");

		// Validate email
		checkEmail(form, errors);

		logger.debug("Leaving: " + proc + "30");
	}

	/**
	 * Validates that mandatory arguments have been set
	 *
	 * @param form
	 * @param errors
	 */
	private void checkMandatoryArgs(UserForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

		logger.debug("Entering: " + proc + "10");

		ValidationUtils.rejectIfEmpty(errors, "email", "user.email.required");
		logger.debug(proc + "20");

		ValidationUtils.rejectIfEmpty(errors, "lastName",
				"user.lastName.required");
		logger.debug("Leaving: " + proc + "30");
	}

	/**
	 * Validates that the user email is unique
	 *
	 * @param form
	 * @param errors
	 */
	private void checkEmail(UserForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkEmail.";
		final String userId = form.getId();
		final String email = form.getEmail();
		int found = 0;

		logger.debug("Entering: " + proc + "10");

		final boolean isUpdating = apiUpdating(userId);
		logger.debug(proc + "20");

		if (isUpdating) {
			final UserEntry user = userRepository.findOne(userId);
			logger.debug(proc + "30");

			if (!user.getEmail().equals(email)) {
				logger.debug(proc + "40");

				found = userRepository.updateEmail(email, userId);
				if (found > 0) {
					errors.rejectValue("email", "user.email.duplicate");
				}
			}
		} else {
			logger.debug(proc + "50");

			found = userRepository.uniqueEmail(email);
			if (found > 0) {
				errors.rejectValue("email", "user.email.duplicate");
			}
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Returns <code>true</code> if the record with the given primary key is
	 * being updated, <code>false</code> otherwise.
	 *
	 * @param userId
	 * @return
	 * @throws IllegalStateException if no record exists for the given primary
	 *             key.
	 */
	private boolean apiUpdating(String userId) {
		boolean result = false;
		if (userId == null) {
			result = false;
		} else {
			final boolean found = userRepository.exists(userId);
			if (!found) {
				throw new IllegalStateException("Invalid primary key");
			}
			result = true;
		}
		return result;
	}
}
