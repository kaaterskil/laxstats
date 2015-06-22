package laxstats.web.people;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import laxstats.api.Common;
import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.web.validators.PostalCodeValidator;

@Service
public class AddressFormValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(AddressFormValidator.class);
   private static final String PACKAGE_NAME = AddressFormValidator.class.getPackage().getName();

   @Autowired
   private PersonQueryRepository personRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return AddressForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final AddressForm form = (AddressForm)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(form, errors);
      logger.debug(proc + "20");

      // Validate type/street/city/region combination
      checkDuplicate(form, errors);
      logger.debug(proc + "30");

      // Validate primary designation
      checkPrimary(form, errors);
      logger.debug(proc + "40");

      // Validate postal code
      checkZipCode(form, errors);
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(AddressForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

      logger.debug("Entering: " + proc + "10");

      ValidationUtils.rejectIfEmpty(errors, "type", "address.type.required");
      logger.debug(proc + "20");

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "address.city.required");
      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that the address is unique for the given person. If the address type, street, city
    * and region combination are unique, then processing continues.
    *
    * @param form
    * @param errors
    */
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
         if (!address.getAddressType().equals(type) ||
            !Common.nvl(address.getAddress1(), "").equalsIgnoreCase(Common.nvl(address1, "")) ||
            !address.getCity().equals(city) ||
            !Common.nvl(address.getRegion(), Region.MA).equals(Common.nvl(region, Region.MA))) {
            logger.debug(proc + "40");

            found =
               personRepository.updateAddress(type, address1, city, region, personId, addressId);
            if (found > 0) {
               errors.rejectValue("city", "address.duplicate");
            }
         }
      }
      else {
         logger.debug(proc + "50");
         found = personRepository.uniqueAddress(type, address1, city, region, personId);
         if (found > 0) {
            errors.rejectValue("city", "address.duplicate");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the primary designation is valid. If the address is given as a primary address
    * and the given person has no other primary address, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkPrimary(AddressForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDuplicate.";
      final String addressId = form.getId();
      final String personId = form.getPersonId();
      final boolean isPrimary = form.isPrimary();

      logger.debug("Entering: " + proc + "10");

      if (isPrimary) {
         final PersonEntry person = personRepository.findOne(personId);
         logger.debug(proc + "20");

         final AddressEntry oldPrimaryAddress = person.primaryAddress();
         logger.debug(proc + "30");

         if (oldPrimaryAddress != null) {
            final boolean isUpdating = apiUpdating(addressId);
            logger.debug(proc + "40");

            // Only proceed with validation if the record is new or if the
            // primary designation has changed

            if (isUpdating) {
               logger.debug(proc + "50");
               if (oldPrimaryAddress.isPrimary() && !oldPrimaryAddress.getId().equals(addressId)) {
                  errors.rejectValue("isPrimary", "address.isPrimary.multiplePrimary");
               }
            }
            else {
               logger.debug(proc + "60");
               if (oldPrimaryAddress.isPrimary()) {
                  errors.rejectValue("isPrimary", "address.isPrimary.multiplePrimary");
               }
            }
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Validates that the postal code is a valid US ZIP code. If the postal code is null or is a
    * valid US ZIP code, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkZipCode(AddressForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkZipCode";
      final String addressId = form.getId();
      final String personId = form.getPersonId();
      final String postalCode = form.getPostalCode();

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or the ZIP code changed.

      if (postalCode != null) {
         logger.debug(proc + "20");

         final boolean isUpdating = apiUpdating(addressId);
         if (isUpdating) {
            logger.debug(proc + "30");

            final PersonEntry person = personRepository.findOne(personId);
            final AddressEntry oldAddress = person.getAddress(addressId);
            final String oldPostalCode = oldAddress == null ? null : oldAddress.getPostalCode();
            if (!postalCode.equals(Common.nvl(oldPostalCode, null))) {
               logger.debug(proc + "40");

               final laxstats.web.validators.Validator validator = PostalCodeValidator.getInstance();
               if (!validator.isValid(postalCode)) {
                  errors.rejectValue("postalCode", "address.postalCode.invalid");
               }
            }
         }
         else {
            logger.debug(proc + "50");

            final laxstats.web.validators.Validator validator = PostalCodeValidator.getInstance();
            if (!validator.isValid(postalCode)) {
               errors.rejectValue("postalCode", "address.postalCode.invalid");
            }
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Returns true if the record with the given primary key is being updated, false otherwise.
    *
    * @param addressId
    * @return
    * @throws IllegalStateException if no record exists with the given primary key.
    */
   private boolean apiUpdating(String addressId) {
      boolean result = false;
      if (addressId == null) {
         result = false;
      }
      else {
         final boolean exists = personRepository.checkAddressExists(addressId);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
