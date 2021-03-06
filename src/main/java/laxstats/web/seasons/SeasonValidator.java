package laxstats.web.seasons;

import java.util.List;

import laxstats.TestUtils;
import laxstats.api.utils.Common;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class SeasonValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(SeasonValidator.class);
   private static final String PACKAGE_NAME = SeasonValidator.class.getPackage()
      .getName();

   private SeasonQueryRepository seasonQueryRepository;

   @Autowired
   public void setSeasonQueryRepository(SeasonQueryRepository seasonQueryRepository) {
      this.seasonQueryRepository = seasonQueryRepository;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return SeasonResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final SeasonResource resource = (SeasonResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate description
      checkDescription(resource, errors);
      logger.debug(proc + "30");

      // Validate endsOn
      checkEndDate(resource, errors);
      logger.debug(proc + "40");

      // Validate date overlap with other seasons
      checkDates(resource, errors);

      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that mandatory arguments have been set
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(SeasonResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String description = target.getDescription();
      final LocalDate startsOn = target.getStartsOnAsLocalDate();

      logger.debug("Entering: " + proc + "10");

      if (TestUtils.isEmptyOrWhitespace(description)) {
         errors.rejectValue("description", "season.description.required");
      }
      logger.debug(proc + "20");

      if (startsOn == null) {
         errors.rejectValue("startsOn", "season.startsOn.required");
      }
      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that <code>description</code> is unique.
    *
    * @param form
    * @param errors
    */
   private void checkDescription(SeasonResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDescription.";
      final String seasonId = target.getId();
      final String description = target.getDescription();
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(seasonId);
      logger.debug(proc + "20");

      if (isUpdating) {
         final SeasonEntry season = seasonQueryRepository.findOne(seasonId);
         logger.debug(proc + "30");

         if ((description != null && season.getDescription() == null) || !season.getDescription()
            .equals(description)) {
            logger.debug(proc + "40");

            found = seasonQueryRepository.updateDescription(description, seasonId);
            if (found > 0) {
               errors.rejectValue("description", "season.description.duplicate");
            }
         }
      }
      else if (!isUpdating) {
         logger.debug(proc + "50");

         found = seasonQueryRepository.uniqueDescription(description);
         if (found > 0) {
            errors.rejectValue("description", "season.description.duplicate");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the season ending date is not less than or equal to the season starting date.
    *
    * @param form
    * @param errors
    */
   private void checkEndDate(SeasonResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkEndDate.";
      final String seasonId = target.getId();
      final LocalDate startsOn = target.getStartsOnAsLocalDate();
      final LocalDate endsOn = target.getEndsOnAsLocalDate();
      final LocalDate eot = Common.EOT.toLocalDate();

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(seasonId);
      logger.debug(proc + "20");

      boolean doValidation = false;
      if (isUpdating) {
         final SeasonEntry season = seasonQueryRepository.findOne(seasonId);
         logger.debug(proc + "30");

         // Test if either the startsOn or endsOn dates have changed
         if (!Common.nvl(season.getEndsOn(), eot)
            .equals(Common.nvl(endsOn, eot)) || !season.getStartsOn()
            .equals(startsOn)) {
            logger.debug(proc + "40");
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation && startsOn != null) {
         final long starts = new DateTime(target.getStartsOn()).getMillis();
         final long ends =
            target.getEndsOn() != null ? new DateTime(target.getEndsOn()).getMillis()
               : Long.MAX_VALUE;

         if (starts >= ends) {
            logger.debug(proc + "55");
            errors.rejectValue("endsOn", "season.endsOn.beforeStart");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that <code>startsOn</code> and <code>endsOn</code> for the given record are unique
    * and do not overlap with other records.
    *
    * @param form
    * @param errors
    */
   private void checkDates(SeasonResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDates.";
      final String seasonId = target.getId();
      final DateTime startsOn = new DateTime(target.getStartsOn());
      final DateTime endsOn = target.getEndsOn() == null ? null : new DateTime(target.getEndsOn());

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(seasonId);
      logger.debug(proc + "20");

      final List<SeasonEntry> list = (List<SeasonEntry>)seasonQueryRepository.findAll();
      logger.debug(proc + "30");

      if (list != null && list.size() > 0) {
         final long startMillis = startsOn.getMillis();
         final long endMillis = endsOn == null ? Long.MAX_VALUE : endsOn.getMillis();

         if (isUpdating) {
            logger.debug(proc + "40");
            for (final SeasonEntry each : list) {
               logger.debug(proc + "42");
               if (!each.getId()
                  .equals(seasonId) && each.overlaps(startMillis, endMillis)) {
                  logger.debug(proc + "44");
                  errors.rejectValue("endsOn", "season.overlap");
                  break;
               }
            }
         }
         else {
            logger.debug(proc + "50");

            for (final SeasonEntry each : list) {
               logger.debug(proc + "52");
               if (each.overlaps(startMillis, endMillis)) {
                  logger.debug(proc + "54");
                  errors.rejectValue("endsOn", "season.overlap");
                  break;
               }
            }
         }
         logger.debug("Leaving: " + proc + "60");
      }
   }

   /**
    * Returns <code>true</code> if the record with the given primary key is being updated,
    * <code>false</code> otherwise.
    *
    * @param seasonId
    * @return boolean
    * @throws IllegalStateException if no record exists for the given primary key
    */
   private boolean apiUpdating(String seasonId) {
      boolean result = false;
      if (seasonId == null) {
         // The primary key is null
         result = false;
      }
      else {
         final boolean found = seasonQueryRepository.exists(seasonId);
         if (!found) {
            // The primary key is invalid
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
