package laxstats.web.sites;

import laxstats.TestUtils;
import laxstats.api.Region;
import laxstats.api.utils.Common;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.web.validators.PostalCodeValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class SiteValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(SiteValidator.class);
   private static String PACKAGE_NAME = SiteValidator.class.getPackage()
      .getName();

   @Autowired
   private SiteQueryRepository siteRepository;

   private PostalCodeValidator postalCodeValidator;

   @Autowired
   public void setPostalCodeValidator(PostalCodeValidator postalCodeValidator) {
      this.postalCodeValidator = postalCodeValidator;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return SiteResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final SiteResource resource = (SiteResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate name/city/region combination
      checkDuplicate(resource, errors);
      logger.debug(proc + "30");

      // Validate postal code
      checkPostalCode(resource, errors);
      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param target
    * @param errors
    */
   private void checkMandatoryArgs(SiteResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String name = target.getName();
      final String city = target.getCity();
      final Region region = target.getRegion();

      logger.debug("Entering: " + proc + "10");

      if (TestUtils.isEmptyOrWhitespace(name)) {
         errors.reject("name", "site.name.required");
      }
      logger.debug(proc + "20");

      if (TestUtils.isEmptyOrWhitespace(city)) {
         errors.rejectValue("city", "site.city.required");
      }
      logger.debug(proc + "30");

      if (region == null) {
         errors.rejectValue("region", "site.region.required");
      }

      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that the name/region combination is unique.
    *
    * @param target
    * @param errors
    */
   private void checkDuplicate(SiteResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDuplicate.";
      final String siteId = target.getId();
      final String city = target.getCity();
      final String name = target.getName();
      final Region region = target.getRegion();
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      // Proceed with validation only if the record is new or the name, city
      // or region have changed.

      final boolean isUpdating = apiUpdating(siteId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final SiteEntry site = siteRepository.findOne(siteId);
         if (!site.getName()
            .equals(name) || (site.getAddress() != null && (!site.getAddress()
            .getCity()
            .equals(city) || !site.getAddress()
            .getRegion()
            .equals(region)))) {
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
    * @param target
    * @param errors
    */
   private void checkPostalCode(SiteResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkPostalCode.";
      final String siteId = target.getId();
      final String postalCode = target.getPostalCode();

      logger.debug("Entering: " + proc + "10");

      if (postalCode != null) {
         logger.debug(proc + "10");

         final boolean isUpating = apiUpdating(siteId);
         if (isUpating) {
            logger.debug(proc + "20");

            final SiteEntry site = siteRepository.findOne(siteId);
            if (site.getAddress() != null && !postalCode.equals(Common.nvl(site.getAddress()
               .getPostalCode(), null))) {
               logger.debug(proc + "30");

               if (!postalCodeValidator.isValid(postalCode)) {
                  errors.rejectValue("postalCode", "site.address.postalCode.invalid");
               }
            }
         }
         else {
            logger.debug(proc + "40");

            // final laxstats.web.validators.Validator validator =
            // PostalCodeValidator.getInstance();
            if (!postalCodeValidator.isValid(postalCode)) {
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
