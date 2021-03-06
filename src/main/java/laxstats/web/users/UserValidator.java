package laxstats.web.users;

import laxstats.TestUtils;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.validators.EmailValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(UserValidator.class);
   private static final String PACKAGE_NAME = UserValidator.class.getPackage()
      .getName();

   @Autowired
   private UserQueryRepository userRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return UserResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final UserResource resource = (UserResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate email
      checkEmail(resource, errors);

      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that mandatory arguments have been set
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(UserResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String email = target.getEmail();
      final String password = target.getPassword();
      final String lastName = target.getLastName();

      logger.debug("Entering: " + proc + "10");

      if (TestUtils.isEmptyOrWhitespace(email)) {
         errors.rejectValue("email", "user.email.required");
      }
      logger.debug(proc + "20");

      if (TestUtils.isEmptyOrWhitespace(password)) {
         errors.rejectValue("password", "user.password.required");
      }
      logger.debug(proc + "30");

      if (TestUtils.isEmptyOrWhitespace(lastName)) {
         errors.rejectValue("lastName", "userLastName.required");
      }
      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that the user email is unique and valid
    *
    * @param form
    * @param errors
    */
   private void checkEmail(UserResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkEmail.";
      final String userId = target.getId();
      final String email = target.getEmail();
      int found = 0;
      boolean doValidation = false;

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(userId);
      logger.debug(proc + "20");

      if (isUpdating) {
         final UserEntry user = userRepository.findOne(userId);
         logger.debug(proc + "30");

         if (!user.getEmail()
            .equals(email)) {
            logger.debug(proc + "40");

            found = userRepository.updateEmail(email, userId);
            if (found > 0) {
               errors.rejectValue("email", "user.email.duplicate");
            }
            else {
               doValidation = true;
            }
         }
      }
      else {
         logger.debug(proc + "50");

         found = userRepository.uniqueEmail(email);
         if (found > 0) {
            errors.rejectValue("email", "user.email.duplicate");
         }
         else {
            doValidation = true;
         }
      }

      if (doValidation) {
         logger.debug(proc + "60");

         final laxstats.web.validators.Validator emailValidator = EmailValidator.getInstance();
         if (!emailValidator.isValid(email)) {
            errors.rejectValue("email", "user.email.invalidEmail");
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Returns <code>true</code> if the record with the given primary key is being updated,
    * <code>false</code> otherwise.
    *
    * @param userId
    * @return
    * @throws IllegalStateException if no record exists for the given primary key.
    */
   private boolean apiUpdating(String userId) {
      boolean result = false;
      if (userId == null) {
         result = false;
      }
      else {
         final boolean found = userRepository.exists(userId);
         if (!found) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
