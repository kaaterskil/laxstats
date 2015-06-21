package laxstats.web.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import laxstats.api.Common;
import laxstats.api.Region;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.web.validators.PostalCodeValidator;

@Service
public class SiteFormValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(SiteFormValidator.class);
   private static String PACKAGE_NAME = SiteFormValidator.class.getPackage().getName();

   @Autowired
   private SiteQueryRepository siteRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return SiteForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final SiteForm form = (SiteForm)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(form, errors);
      logger.debug(proc + "20");

      // Validate name/city/region combination
      checkDuplicate(form, errors);
      logger.debug(proc + "30");

      // Validate postal code
      checkPostalCode(form, errors);
      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(SiteForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";

      logger.debug("Entering: " + proc + "10");

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "site.name.required");
      logger.debug(proc + "20");

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "site.city.required");
      logger.debug(proc + "30");

      ValidationUtils.rejectIfEmpty(errors, "region", "site.region.required");

      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that the name/region combination is unique.
    *
    * @param form
    * @param errors
    */
   private void checkDuplicate(SiteForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDuplicate.";
      final String siteId = form.getId();
      final String city = form.getCity();
      final String name = form.getName();
      final Region region = form.getRegion();
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or the name, city
      // or region have changed.

      final boolean isUpdating = apiUpdating(siteId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final SiteEntry site = siteRepository.findOne(siteId);
         if (!site.getName().equals(name) ||
            (site.getAddress() != null && (!site.getAddress().getCity().equals(city) ||
               !site.getAddress().getRegion().equals(region)))) {
            logger.debug(proc + "40");

            found = siteRepository.updateName(name, city, region, siteId);
            if (found > 0) {
               errors.rejectValue("name", "site.duplicate");
            }
         }
      }
      else {
         logger.debug(proc + "50");

         found = siteRepository.uniqueName(name, city, region);
         if (found > 0) {
            errors.rejectValue("name", "site.duplicate");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the postal code is a valid US ZIP Code. IF the given postal code is not null
    * and is a valid US ZIP Code, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkPostalCode(SiteForm form, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkPostalCode.";
      final String siteId = form.getId();
      final String postalCode = form.getPostalCode();

      logger.debug("Entering: " + proc + "10");

      if (postalCode != null) {
         logger.debug(proc + "10");

         final boolean isUpating = apiUpdating(siteId);
         if (isUpating) {
            logger.debug(proc + "20");

            final SiteEntry site = siteRepository.findOne(siteId);
            if (site.getAddress() != null &&
               !postalCode.equals(Common.nvl(site.getAddress().getPostalCode(), null))) {
               logger.debug(proc + "30");

               final laxstats.web.validators.Validator validator = PostalCodeValidator.getInstance();
               if (!validator.isValid(postalCode)) {
                  errors.rejectValue("postalCode", "site.address.postalCode.invalid");
               }
            }
         }
         else {
            logger.debug(proc + "40");

            final laxstats.web.validators.Validator validator = PostalCodeValidator.getInstance();
            if (!validator.isValid(postalCode)) {
               errors.rejectValue("postalCode", "site.address.postalCode.invalid");
            }
         }
      }
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Returns true if the record with the given primary key is being updated, false otherwise.
    *
    * @param siteId
    * @return
    * @throws IllegalStateException if no record exists with the given primary key.
    */
   private boolean apiUpdating(String siteId) {
      boolean result = false;
      if (siteId == null) {
         result = false;
      }
      else {
         final boolean exists = siteRepository.exists(siteId);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
