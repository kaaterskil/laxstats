package laxstats.web.violations;

import laxstats.TestUtils;
import laxstats.api.violations.PenaltyCategory;
import laxstats.query.violations.ViolationEntry;
import laxstats.query.violations.ViolationQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ViolationValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(ViolationValidator.class);
   private static final String PACKAGE_NAME = ViolationValidator.class.getPackage().getName();

   @Autowired
   private ViolationQueryRepository violationRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return ViolationResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final ViolationResource resource = (ViolationResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate name
      checkName(resource, errors);

      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(ViolationResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String name = target.getName();
      final PenaltyCategory category = target.getCategory();

      logger.debug("Entering: " + proc + "10");

      if (TestUtils.isEmptyOrWhitespace(name)) {
         errors.rejectValue("name", "violation.name.required");
      }
      logger.debug(proc + "20");

      if (category == null) {
         errors.rejectValue("category", "violation.category.required");
      }

      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that the violation name is unique.
    *
    * @param form
    * @param errors
    */
   private void checkName(ViolationResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkName.";
      final String violationId = target.getId();
      final String name = target.getName();
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(violationId);
      logger.debug(proc + "20");

      if (isUpdating) {
         final ViolationEntry violation = violationRepository.findOne(violationId);
         logger.debug(proc + "30");

         if ((name != null && violation.getName() == null) || !violation.getName().equals(name)) {
            logger.debug(proc + "40");

            found = violationRepository.updateName(name, violationId);
            if (found > 0) {
               errors.rejectValue("name", "violation.name.duplicate");
            }
         }
      }
      else {
         logger.debug(proc + "50");

         found = violationRepository.uniqueName(name);
         if (found > 0) {
            errors.rejectValue("name", "violation.name.duplicate");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Returns <code>true</code> if he record with the given primary key is being updated,
    * <code>false</code> otherwise.
    *
    * @param violationId
    * @return boolean
    * @throws IllegalStateException if no record exists for the given primary key.
    */
   private boolean apiUpdating(String violationId) {
      boolean result = false;
      if (violationId == null) {
         result = false;
      }
      else {
         final boolean found = violationRepository.exists(violationId);
         if (!found) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }

}
