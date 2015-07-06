package laxstats.web.people;

import laxstats.TestUtils;
import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.api.utils.Common;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.web.validators.PostalCodeValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AddressValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(AddressValidator.class);
   private static final String PACKAGE_NAME = AddressValidator.class.getPackage().getName();

   @Autowired
   private PersonQueryRepository personRepository;

   private PostalCodeValidator postalCodeValidator;

   @Autowired
   public void setPostalCodeValidator(PostalCodeValidator postalCodeValidator) {
      this.postalCodeValidator = postalCodeValidator;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return AddressResource.class.equals(clazz) || AddressForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(target, errors);
      logger.debug(proc + "20");

      // Validate type/street/city/region combination
      checkDuplicate(target, errors);
      logger.debug(proc + "30");

      // Validate primary designation
      checkPrimary(target, errors);
      logger.debug(proc + "40");

      // Validate postal code
      checkZipCode(target, errors);
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      AddressType type = null;
      String city = null;

      if (target instanceof AddressResource) {
         final AddressResource resource = (AddressResource)target;
         type = resource.getType();
         city = resource.getCity();
      }
      else if (target instanceof AddressForm) {
         final AddressForm form = (AddressForm)target;
         type = form.getType();
         city = form.getCity();
      }

      logger.debug("Entering: " + proc + "10");

      if (type == null) {
         errors.rejectValue("type", "address.type.required");
      }
      logger.debug(proc + "20");

      if (TestUtils.isEmptyOrWhitespace(city)) {
         errors.rejectValue("city", "address.city.required");
      }
      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that the address is unique for the given person. If the address type, street, city
    * and region combination are unique, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkDuplicate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDuplicate.";
      String addressId = null;
      AddressType type = null;
      String address1 = null;
      String city = null;
      Region region = null;
      String personId = null;
      int found = 0;

      if (target instanceof AddressResource) {
         final AddressResource resource = (AddressResource)target;
         addressId = resource.getId();
         personId = resource.getPersonId();
         type = resource.getType();
         address1 = resource.getAddress1();
         city = resource.getCity();
         region = resource.getRegion();
      }
      else if (target instanceof AddressForm) {
         final AddressForm form = (AddressForm)target;
         addressId = form.getId();
         personId = form.getPersonId();
         type = form.getType();
         address1 = form.getAddress1();
         city = form.getCity();
         region = form.getRegion();
      }

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
   private void checkPrimary(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkPrimary.";
      String addressId = null;
      String personId = null;
      boolean isPrimary = false;

      if (target instanceof AddressResource) {
         final AddressResource resource = (AddressResource)target;
         addressId = resource.getId();
         personId = resource.getPersonId();
         isPrimary = resource.isPrimary();
      }
      else if (target instanceof AddressForm) {
         final AddressForm form = (AddressForm)target;
         addressId = form.getId();
         personId = form.getPersonId();
         isPrimary = form.isPrimary();
      }

      logger.debug("Entering: " + proc + "10");

      if (isPrimary) {
         final PersonEntry person = personRepository.findOne(personId);
         logger.debug(proc + "20");

         AddressEntry oldPrimaryAddress = null;
         if (person != null) {
            oldPrimaryAddress = person.primaryAddress();
         }
         logger.debug(proc + "30");

         if (oldPrimaryAddress != null) {
            final boolean isUpdating = apiUpdating(addressId);
            logger.debug(proc + "40");

            // Only proceed with validation if the record is new or if the
            // primary designation has changed

            if (isUpdating) {
               logger.debug(proc + "50");
               if (oldPrimaryAddress.isPrimary() && !oldPrimaryAddress.getId().equals(addressId)) {
                  errors.rejectValue("primary", "address.primary.multiplePrimary");
               }
            }
            else {
               logger.debug(proc + "60");
               if (oldPrimaryAddress.isPrimary()) {
                  errors.rejectValue("primary", "address.primary.multiplePrimary");
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
   private void checkZipCode(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkZipCode.";
      String addressId = null;
      String personId = null;
      String postalCode = null;

      if (target instanceof AddressResource) {
         final AddressResource resource = (AddressResource)target;
         addressId = resource.getId();
         personId = resource.getPersonId();
         postalCode = resource.getPostalCode();
      }
      else if (target instanceof AddressForm) {
         final AddressForm form = (AddressForm)target;
         addressId = form.getId();
         personId = form.getPersonId();
         postalCode = form.getPostalCode();
      }

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

               if (!postalCodeValidator.isValid(postalCode)) {
                  errors.rejectValue("postalCode", "address.postalCode.invalid");
               }
            }
         }
         else {
            logger.debug(proc + "50");

            if (!postalCodeValidator.isValid(postalCode)) {
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