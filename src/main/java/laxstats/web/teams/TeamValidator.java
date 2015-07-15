package laxstats.web.teams;

import laxstats.TestUtils;
import laxstats.api.Region;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class TeamValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(TeamValidator.class);
   private static final String PACKAGE_NAME = TeamValidator.class.getPackage()
      .getName();

   @Autowired
   private TeamQueryRepository teamRepository;

   @Autowired
   private SiteQueryRepository siteRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return TeamResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final TeamResource resource = (TeamResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory arguments
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate team/sponsor/gender/letter combination
      checkTeam(resource, errors);
      logger.debug(proc + "30");

      // Validate home site
      checkHomeSite(resource, errors);

      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Validates that mandatory arguments have been set
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(TeamResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String sponsor = target.getSponsor();
      final String name = target.getName();
      final TeamGender gender = target.getGender();
      final Letter letter = target.getLetter();
      final Region region = target.getRegion();

      logger.debug("Entering: " + proc + "10");

      if (TestUtils.isEmptyOrWhitespace(sponsor)) {
         errors.rejectValue("sponsor", "team.sponsor.required");
      }
      logger.debug(proc + "20");

      if (TestUtils.isEmptyOrWhitespace(name)) {
         errors.rejectValue("name", "team.name.required");
      }
      logger.debug(proc + "30");

      if (gender == null) {
         errors.rejectValue("gender", "team.gender.required");
      }
      logger.debug(proc + "40");

      if (letter == null) {
         errors.rejectValue("letter", "team.letter.required");
      }
      logger.debug(proc + "50");

      if (region == null) {
         errors.rejectValue("region", "team.region.required");
      }

      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the sponsor/name/gender/letter combination are unique
    *
    * @param form
    * @param errors
    */
   private void checkTeam(TeamResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkTeam.";
      final String teamId = target.getId();
      final String sponsor = target.getSponsor();
      final String name = target.getName();
      final TeamGender gender = target.getGender();
      final Letter letter = target.getLetter();
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(teamId);
      logger.debug(proc + "20");

      if (isUpdating) {
         final TeamEntry team = teamRepository.findOne(teamId);
         logger.debug(proc + "30");

         if (!team.getSponsor()
            .equals(sponsor) || !team.getName()
            .equals(name) || !team.getGender()
            .equals(gender) || !team.getLetter()
            .equals(letter)) {
            logger.debug(proc + "40");

            found = teamRepository.updateTeam(sponsor, name, gender, letter, teamId);
            if (found > 0) {
               errors.rejectValue("name", "team.duplicate");
            }
         }
      }
      else {
         logger.debug(proc + "50");
         found = teamRepository.uniqueTeam(sponsor, name, gender, letter);
         if (found > 0) {
            errors.rejectValue("name", "team.duplicate");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the home site, if set and references an address, is in the same region.
    *
    * @param form
    * @param errors
    */
   private void checkHomeSite(TeamResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkHomeSite.";
      final String teamId = target.getId();
      final Region region = target.getRegion();
      final String siteId = target.getHomeSite();

      logger.debug("Entering: " + proc + "10");

      if (siteId != null) {
         logger.debug(proc + "20");

         final SiteEntry site = siteRepository.findOne(siteId);
         if (site.getAddress() != null) {
            logger.debug(proc + "30");

            final Region siteRegion = site.getAddress()
               .getRegion();
            final boolean isUpdating = apiUpdating(teamId);
            logger.debug(proc + "40");

            if (isUpdating) {
               logger.debug(proc + "50");

               final TeamEntry team = teamRepository.findOne(teamId);
               if ((team.getHomeSite() == null || !team.getHomeSite()
                  .equals(site)) && !region.equals(siteRegion)) {
                  errors.rejectValue("homeSite", "team.homeSite.invalid");
               }
            }
            else {
               logger.debug(proc + "60");
               if (!region.equals(siteRegion)) {
                  errors.rejectValue("homeSite", "team.homeSite.invalid");
               }
            }
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Returns <code>true</code> if the record with the given primary key is being updated,
    * <code>false</code> otherwise.
    *
    * @param teamId
    * @return
    * @throws IllegalStateException if no record exists with the given primary key.
    */
   private boolean apiUpdating(String teamId) {
      boolean result = false;
      if (teamId == null) {
         result = false;
      }
      else {
         final boolean exists = teamRepository.exists(teamId);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
