package laxstats.web.seasons;

import java.util.List;

import laxstats.api.Common;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class SeasonFormValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(SeasonFormValidator.class);
   private static final String PACKAGE_NAME = SeasonFormValidator.class.getPackage().getName();

   private SeasonQueryRepository seasonQueryRepository;

   @Autowired
   public void setSeasonQueryRepository(SeasonQueryRepository seasonQueryRepository) {
      this.seasonQueryRepository = seasonQueryRepository;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return SeasonForm.class.equals(clazz) || SeasonInfo.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(target, errors);
      logger.debug(proc + "20");

      // Validate description
      checkDescription(target, errors);
      logger.debug(proc + "30");

      // Validate endsOn
      checkEndDate(target, errors);
      logger.debug(proc + "40");

      // Validate date overlap with other seasons
      checkDates(target, errors);

      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates that mandatory arguments have been set
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      String description = null;
      LocalDate startsOn = null;

      if (target instanceof SeasonForm) {
         final SeasonForm seasonForm = (SeasonForm)target;
         description = seasonForm.getDescription();
         startsOn = seasonForm.getStartsOn();
      }
      else if (target instanceof SeasonInfo) {
         final SeasonInfo seasonInfo = (SeasonInfo)target;
         description = seasonInfo.getDescription();
         startsOn = LocalDate.parse(seasonInfo.getStartsOn());
      }

      logger.debug("Entering: " + proc + "10");

      if (description == null || description.length() == 0) {
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
   private void checkDescription(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDescription.";
      String seasonId = null;
      String description = null;
      int found = 0;

      if (target instanceof SeasonForm) {
         final SeasonForm seasonForm = (SeasonForm)target;
         seasonId = seasonForm.getId();
         description = seasonForm.getDescription();
      }
      else if (target instanceof SeasonInfo) {
         final SeasonInfo seasonInfo = (SeasonInfo)target;
         seasonId = seasonInfo.getId();
         description = seasonInfo.getDescription();
      }

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(seasonId);
      logger.debug(proc + "20");

      if (isUpdating) {
         final SeasonEntry season = seasonQueryRepository.findOne(seasonId);
         logger.debug(proc + "30");

         if ((description != null && season.getDescription() == null) ||
                  !season.getDescription().equals(description)) {
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
    * Validates that <code>endsOn</code>, when set, is not less than or equal to the value for
    * <code>startsOn</code> on the same Season record.
    *
    * @param form
    * @param errors
    */
   private void checkEndDate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkEndDate.";
      String seasonId = null;
      LocalDate startsOn = null;
      LocalDate endsOn = null;
      final LocalDate eot = Common.EOT.toLocalDate();

      if (target instanceof SeasonForm) {
         final SeasonForm seasonForm = (SeasonForm)target;
         seasonId = seasonForm.getId();
         startsOn = seasonForm.getStartsOn();
         endsOn = seasonForm.getEndsOn();
      }
      else if (target instanceof SeasonInfo) {
         final SeasonInfo seasonInfo = (SeasonInfo)target;
         seasonId = seasonInfo.getId();
         startsOn = LocalDate.parse(seasonInfo.getStartsOn());
         endsOn = LocalDate.parse(seasonInfo.getEndsOn());
      }

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(seasonId);
      logger.debug(proc + "20");

      boolean doValidation = false;
      if (isUpdating) {
         final SeasonEntry season = seasonQueryRepository.findOne(seasonId);
         logger.debug(proc + "30");

         // Test if either the startsOn or endsOn dates have changed
         if (!Common.nvl(season.getEndsOn(), eot).equals(Common.nvl(endsOn, eot)) ||
                  !season.getStartsOn().equals(startsOn)) {
            logger.debug(proc + "40");
            doValidation = true;
         }
      }
      else {
         logger.debug(proc + "50");
         doValidation = true;
      }

      if (doValidation && startsOn != null &&
         (startsOn.isAfter(Common.nvl(endsOn, eot)) || startsOn.isEqual(Common.nvl(endsOn, eot)))) {
         errors.rejectValue("endsOn", "season.endsOn.beforeStart");
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
   private void checkDates(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkDates.";
      String seasonId = null;
      LocalDate startsOn = null;
      LocalDate endsOn = null;

      if (target instanceof SeasonForm) {
         final SeasonForm seasonForm = (SeasonForm)target;
         seasonId = seasonForm.getId();
         startsOn = seasonForm.getStartsOn();
         endsOn = Common.nvl(seasonForm.getEndsOn(), Common.EOT.toLocalDate());
      }
      else if (target instanceof SeasonInfo) {
         final SeasonInfo seasonInfo = (SeasonInfo)target;
         seasonId = seasonInfo.getId();
         startsOn = LocalDate.parse(seasonInfo.getStartsOn());
         endsOn = Common.nvl(LocalDate.parse(seasonInfo.getEndsOn()), Common.EOT.toLocalDate());
      }

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(seasonId);
      logger.debug(proc + "20");

      final List<SeasonEntry> list = (List<SeasonEntry>)seasonQueryRepository.findAll();
      logger.debug(proc + "30");

      if (list != null && list.size() > 0) {
         if (isUpdating) {
            logger.debug(proc + "40");
            for (final SeasonEntry each : list) {
               if (!each.getId().equals(seasonId) && each.overlaps(startsOn, endsOn)) {
                  errors.rejectValue("endsOn", "season.overlap");
                  break;
               }
            }
         }
         else {
            logger.debug(proc + "50");
            for (final SeasonEntry each : list) {
               if (each.overlaps(startsOn, endsOn)) {
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
