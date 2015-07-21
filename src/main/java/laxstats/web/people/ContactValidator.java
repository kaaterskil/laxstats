package laxstats.web.people;

import laxstats.TestUtils;
import laxstats.api.people.ContactMethod;
import laxstats.query.people.ContactEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.web.validators.EmailValidator;
import laxstats.web.validators.TelephoneValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ContactValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(ContactValidator.class);
   private static final String PACKAGE_NAME = ContactValidator.class.getPackage()
      .getName();

   PersonQueryRepository personQueryRepository;

   @Autowired
   public void setPersonQueryRepository(PersonQueryRepository personQueryRepository) {
      this.personQueryRepository = personQueryRepository;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return ContactResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final ContactResource resource = (ContactResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate method/value combination
      checkDuplicate(resource, errors);
      logger.debug(proc + "30");

      // Validate primary designation
      checkPrimary(resource, errors);
      logger.debug(proc + "40");

      // Validate value
      checkValue(resource, errors);
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(ContactResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final ContactMethod method = target.getMethod();
      final String value = target.getValue();

      logger.debug("Entering: " + proc + "10");

      if (method == null) {
         errors.rejectValue("method", "contact.method.required");
      }
      logger.debug(proc + "20");

      if (TestUtils.isEmptyOrWhitespace(value)) {
         errors.rejectValue("value", "contact.value.required");
      }
      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that the value is unique for the given contact method.
    *
    * @param form
    * @param errors
    */
   private void checkDuplicate(ContactResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDuplicate.";
      final String contactId = target.getId();
      final ContactMethod method = target.getMethod();
      final String value = target.getValue();
      final String personId = target.getPersonId();
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or if the contact
      // method or value has changed.

      final boolean isUpdating = apiUpdating(contactId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final PersonEntry person = personQueryRepository.findOne(personId);
         final ContactEntry contact = person.getContact(contactId);
         if (!contact.getMethod()
            .equals(method) || !contact.getValue()
            .equalsIgnoreCase(value)) {
            logger.debug(proc + "40");

            found = personQueryRepository.updateContact(method, value, personId, contactId);
            if (found > 0) {
               errors.rejectValue("value", "contact.value.duplicate");
            }
         }
      }
      else {
         logger.debug(proc + "50");

         found = personQueryRepository.uniqueContact(method, value, personId);
         if (found > 0) {
            errors.rejectValue("value", "contact.value.duplicate");
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
   private void checkPrimary(ContactResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkPrimary.";
      final String contactId = target.getId();
      final String personId = target.getPersonId();
      final boolean isPrimary = target.isPrimary();

      logger.debug("Entering: " + proc + "10");

      if (isPrimary) {
         final PersonEntry person = personQueryRepository.findOne(personId);
         logger.debug(proc + "20");

         final ContactEntry oldPrimaryContact = person.primaryContact();
         logger.debug(proc + "30");

         if (oldPrimaryContact != null) {
            final boolean isUpdating = apiUpdating(contactId);
            logger.debug(proc + "40");

            // Only proceed with validation if the record is new or if the
            // primary designation has changed

            if (isUpdating) {
               logger.debug(proc + "50");
               if (oldPrimaryContact.isPrimary() && !oldPrimaryContact.getId()
                  .equals(contactId)) {
                  errors.rejectValue("primary", "contact.primary.multiplePrimary");
               }
            }
            else {
               logger.debug(proc + "60");
               if (oldPrimaryContact.isPrimary()) {
                  errors.rejectValue("primary", "contact.primary.multiplePrimary");
               }
            }
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Validates the contact value. More specifically, validates that the value is a well-formed
    * email address or a valid North American telephone number.
    *
    * @param form
    * @param errors
    */
   private void checkValue(ContactResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkValue.";
      final String contactId = target.getId();
      final ContactMethod method = target.getMethod();
      final String value = target.getValue();
      final String personId = target.getPersonId();
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or if the value has
      // changed.

      final boolean isUpdating = apiUpdating(contactId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final PersonEntry person = personQueryRepository.findOne(personId);
         final ContactEntry contact = person.getContact(contactId);
         if (!contact.getValue()
            .equalsIgnoreCase(value)) {
            logger.debug(proc + "40");
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation && method != null) {
         if (method.equals(ContactMethod.EMAIL)) {
            logger.debug(proc + "60");

            final laxstats.web.validators.Validator emailValidator = EmailValidator.getInstance();
            if (!emailValidator.isValid(value)) {
               errors.rejectValue("value", "contact.value.invalidEmail");
            }
         }
         else {
            logger.debug(proc + "70");

            final laxstats.web.validators.Validator telephoneValidator =
               TelephoneValidator.getInstance();
            if (!telephoneValidator.isValid(value)) {
               errors.rejectValue("value", "contact.value.invalidTelephone");
            }
         }
      }
      logger.debug("Leaving: " + proc + "80");
   }

   /**
    * Returns true if the record with the given primary key is being updated, false otherwise.
    *
    * @param contactId
    * @return
    * @throws IllegalStateException if no record exists with the given primary key.
    */
   private boolean apiUpdating(String contactId) {
      boolean result = false;
      if (contactId == null) {
         result = false;
      }
      else {
         final boolean exists = personQueryRepository.checkContactExists(contactId);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
