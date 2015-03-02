package laxstats.web.people;

import laxstats.api.Common;
import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddressFormValidator implements Validator {
	private static final Logger logger = LoggerFactory
			.getLogger(AddressFormValidator.class);
	private static final String PACKAGE_NAME = AddressFormValidator.class
			.getPackage().getName();

	@Autowired
	private PersonQueryRepository personRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return AddressForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final String proc = PACKAGE_NAME + ".validate.";
		final AddressForm form = (AddressForm) target;

		logger.debug("Entering: " + proc + "10");

		// Validate mandatory arguments
		checkMandatoryArgs(form, errors);
		logger.debug(proc + "20");

		// Validate type/street/city/region combination
		checkDuplicate(form, errors);
		logger.debug("Leaving: " + proc + "30");
	}

	private void checkMandatoryArgs(AddressForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

		logger.debug("Entering: " + proc + "10");

		ValidationUtils.rejectIfEmpty(errors, "type", "address.type.required");
		logger.debug(proc + "20");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city",
				"address.city.required");
		logger.debug("Leaving: " + proc + "40");
	}

	private void checkDuplicate(AddressForm form, Errors errors) {
		final String proc = PACKAGE_NAME + ".checkDuplicate.";
		final String addressId = form.getId();
		final AddressType type = form.getType();
		final String address1 = form.getAddress1();
		final String city = form.getCity();
		final Region region = form.getRegion();
		final String personId = form.getPersonId();
		int found = 0;

		logger.debug("Entering: " + proc + "10");

		// Proceed with validation only if the record is new or the address
		// type, street, city or region have changed.

		final boolean isUpdating = apiUpdating(addressId);
		logger.debug(proc + "20");

		if (isUpdating) {
			logger.debug(proc + "30");

			final PersonEntry person = personRepository.findOne(personId);
			final AddressEntry address = person.getAddress(addressId);
			if (!address.getAddressType().equals(type)
					|| !Common.nvl(address.getAddress1(), "").equalsIgnoreCase(
							Common.nvl(address1, ""))
					|| !address.getCity().equals(city)
					|| !Common.nvl(address.getRegion(), Region.MA).equals(
							Common.nvl(region, Region.MA))) {
				logger.debug(proc + "40");

				found = personRepository.updateAddress(type, address1, city,
						region, personId, addressId);
				if (found > 0) {
					errors.rejectValue("city", "address.duplicate");
				}
			}
		} else {
			logger.debug(proc + "50");

			found = personRepository.uniqueAddress(type, address1, city,
					region, personId);
			if (found > 0) {
				errors.rejectValue("city", "address.duplicate");
			}
		}
		logger.debug("Leaving: " + proc + "60");
	}

	/**
	 * Returns true if the record with the given primary key is being updated,
	 * false otherwise.
	 *
	 * @param addressId
	 * @return
	 * @throws IllegalStateException if no record exists with the given primary
	 *             key.
	 */
	private boolean apiUpdating(String addressId) {
		boolean result = false;
		if (addressId == null) {
			result = false;
		} else {
			final boolean exists = personRepository
					.checkAddressExists(addressId);
			if (!exists) {
				throw new IllegalStateException("Invalid primary key");
			}
			result = true;
		}
		return result;
	}
}
