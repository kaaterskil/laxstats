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
      return ViolationResource.class.equals(clazz) || ViolationForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(target, errors);
      logger.debug(proc + "20");

      // Validate name
      checkName(target, errors);

      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      String name = null;
      PenaltyCategory category = null;

      if (target instanceof ViolationResource) {
         final ViolationResource resource = (ViolationResource)target;
         name = resource.getName();
         category = resource.getCategory();
      }
      else if (target instanceof ViolationForm) {
         final ViolationForm form = (ViolationForm)target;
         name = form.getName();
         category = form.getCategory();
      }

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
   private void checkName(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkName.";
      String violationId = null;
      String name = null;
      int found = 0;

      if (target instanceof ViolationResource) {
         final ViolationResource resource = (ViolationResource)target;
         violationId = resource.getId();
         name = resource.getName();
      }
      else if (target instanceof ViolationForm) {
         final ViolationForm form = (ViolationForm)target;
         violationId = form.getId();
         name = form.getName();
      }

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
