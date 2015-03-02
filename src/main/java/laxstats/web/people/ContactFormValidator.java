package laxstats.web.people;

import laxstats.api.people.ContactMethod;
import laxstats.query.people.ContactEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.web.validators.EmailValidator;
import laxstats.web.validators.TelephoneValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContactFormValidator implements Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(ContactFormValidator.class);
	private static final String PACKAGE_NAME = ContactFormValidator.class
			.getPackage().getName();

	@Autowired
	PersonQueryRepository personRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return ContactForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final String proc = PACKAGE_NAME + ".validate.";
		final ContactForm form = (ContactForm) target;

		logger.debug("Entering: " + proc + "10");

		// Validate mandatory arguments
		checkMandatoryArgs(form, errors);
		logger.debug(proc + "20");

		// Validate method/value combination
		checkDuplicate(form, errors);
		logger.debug(proc + "30");

		// Validate value
		checkValue(form, errors);
		logger.debug("Leaving: " + proc + "40");
	}

	/**
	 * Validates that mandatory arguments have been set.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkMandatoryArgs(ContactForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

		logger.debug("Entering: " + proc + "10");

		ValidationUtils.rejectIfEmpty(errors, "method",
				"contact.method.required");
		logger.debug(proc + "20");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value",
				"contact.value.required");
		logger.debug("Leaving: " + proc + "30");
	}

	/**
	 * Validates that the value is unique for the given contact method.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkDuplicate(ContactForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkDuplicate.";
		final String contactId = form.getId();
		final ContactMethod method = form.getMethod();
		final String value = form.getValue();
		final String personId = form.getPersonId();
		int found = 0;

		logger.debug("Entering: " + proc + "10");

		// Proceed with validation only if the record is new or if the contact
		// method or value has changed.

		final boolean isUpdating = apiUpdating(contactId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final PersonEntry person = personRepository.findOne(personId);
			final ContactEntry contact = person.getContact(contactId);
			if (!contact.getMethod().equals(method)
					|| !contact.getValue().equalsIgnoreCase(value)) {
				logger.debug(proc + "40");

				found = personRepository.updateContact(method, value, personId,
						contactId);
				if (found > 0) {
					errors.rejectValue("value", "contact.value.duplicate");
				}
			}
		} else {
			logger.debug(proc + "50");

			found = personRepository.uniqueContact(method, value, personId);
			if (found > 0) {
				errors.rejectValue("value", "contact.value.duplicate");
			}
		}
		logger.debug("Leaving: " + proc + "60");

	}

	/**
	 * Validates the contact value. More specifically, validates that the value
	 * is a well-formed email address or a valid North American telephone
	 * number.
	 *
	 * @param form
	 * @param errors
	 */
	private void checkValue(ContactForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkValue.";
		final String contactId = form.getId();
		final ContactMethod method = form.getMethod();
		final String value = form.getValue();
		final String personId = form.getPersonId();
		boolean doValidation = false;

		logger.debug("Entering: " + proc + "10");

		// Proceed with validation only if the record is new or if the value has
		// changed.

		final boolean isUpdating = apiUpdating(contactId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final PersonEntry person = personRepository.findOne(personId);
			final ContactEntry contact = person.getContact(contactId);
			if (!contact.getValue().equalsIgnoreCase(value)) {
				logger.debug(proc + "40");
				doValidation = true;
			}
		} else {
			logger.debug(proc + "50");
			doValidation = true;
		}

		if (doValidation) {
			if (method.equals(ContactMethod.EMAIL)) {
				logger.debug(proc + "60");

				final laxstats.web.validators.Validator emailValidator = EmailValidator
						.getInstance();
				if (!emailValidator.isValid(value)) {
					errors.rejectValue("value", "contact.value.invalidEmail");
				}
			} else {
				logger.debug(proc + "70");

				final laxstats.web.validators.Validator telephoneValidator = TelephoneValidator
						.getInstance();
				if (!telephoneValidator.isValid(value)) {
					errors.rejectValue("value",
							"contact.value.invalidTelephone");
				}
			}
		}
		logger.debug("Leaving: " + proc + "80");
	}

	/**
	 * Returns true if the record with the given primary key i being updated,
	 * false otherwise.
	 *
	 * @param contactId
	 * @return
	 * @throws IllegalStateException if no record exists with the given primary
	 *             key.
	 */
	private boolean apiUpdating(String contactId) {
		boolean result = false;
		if (contactId == null) {
			result = false;
		} else {
			final boolean exists = personRepository
					.checkContactExists(contactId);
			if (!exists) {
				throw new IllegalStateException("Invalid primary key");
			}
			result = true;
		}
		return result;
	}
}
