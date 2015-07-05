package laxstats.web.people;

import laxstats.TestUtils;
import laxstats.api.people.Gender;
import laxstats.api.players.Role;
import laxstats.api.teams.TeamGender;
import laxstats.api.utils.Common;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.players.PlayerEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class PersonValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(PersonValidator.class);
   private static final String PACKAGE_NAME = PersonValidator.class.getPackage().getName();

   @Autowired
   PersonQueryRepository personRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return PersonResource.class.equals(clazz) || PersonForm.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory args
      checkMandatoryArgs(target, errors);
      logger.debug(proc + "20");

      // Validate birth date
      checkBirthDate(target, errors);
      logger.debug(proc + "30");

      // Check gender
      checkGender(target, errors);
      logger.debug(proc + "40");

      // Check parent release sent date
      checkParentReleaseSentOn(target, errors);
      logger.debug(proc + "50");

      // Check parent release received date
      checkParentReleaseReceivedOn(target, errors);
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param form
    * @param errors
    */
   private void checkMandatoryArgs(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      String lastName = null;

      if (target instanceof PersonResource) {
         lastName = ((PersonResource)target).getLastName();
      }
      else if (target instanceof PersonForm) {
         lastName = ((PersonForm)target).getLastName();
      }

      logger.debug("Entering: " + proc + "10");

      if (TestUtils.isEmptyOrWhitespace(lastName)) {
         errors.rejectValue("lastName", "person.lastName.required");
      }
      logger.debug("Leaving: " + proc + "20");
   }

   /**
    * Validates that a birth date is valid. If the played seasons collection is not empty and the
    * birth date is <= the earliest season, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkBirthDate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkBirthDate.";
      String personId = null;
      LocalDate birthDate = null;
      final LocalDate eot = Common.EOT.toLocalDate();

      if (target instanceof PersonResource) {
         final PersonResource r = (PersonResource)target;
         personId = r.getId();
         birthDate = r.getBirthdate() == null ? null : LocalDate.parse(r.getBirthdate());
      }
      else if (target instanceof PersonForm) {
         final PersonForm form = (PersonForm)target;
         personId = form.getId();
         birthDate = form.getBirthdate();
      }

      logger.debug("Entering: " + proc + "10");

      if (birthDate != null) {
         logger.debug(proc + "20");

         final boolean isUpdating = apiUpdating(personId);
         logger.debug(proc + "30");

         if (isUpdating) {
            logger.debug(proc + "40");

            final PersonEntry person = personRepository.findOne(personId);
            LocalDate startsOn = LocalDate.now();

            if (!person.getPlayedSeasons().isEmpty() &&
               !Common.nvl(person.getBirthdate(), eot).equals(birthDate)) {
               logger.debug(proc + "50");

               for (final PlayerEntry each : person.getPlayedSeasons()) {
                  final TeamSeasonEntry team = each.getTeamSeason();
                  if (team.getStartsOn().isBefore(startsOn)) {
                     startsOn = team.getStartsOn();
                  }
               }
            }
            if (birthDate.isAfter(startsOn)) {
               errors.rejectValue("birthdate", "person.birthdate.invalid");
            }
         }
         else {
            logger.debug(proc + "60");
            final LocalDate today = LocalDate.now();
            if (birthDate.isAfter(today)) {
               errors.rejectValue("birthdate", "person.birthdate.invalid");
            }
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Validates that a gender is valid. If the played seasons collection is not empty and the person
    * is an athlete and their gender equals the team gender, then processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkGender(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkGender.";
      String personId = null;
      Gender gender = null;
      Gender teamGender = null;

      if (target instanceof PersonResource) {
         final PersonResource resource = (PersonResource)target;
         personId = resource.getId();
         gender = resource.getGender();
      }
      else if (target instanceof PersonForm) {
         final PersonForm form = (PersonForm)target;
         personId = form.getId();
         gender = form.getGender();
      }

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(personId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final PersonEntry person = personRepository.findOne(personId);
         if (!person.getPlayedSeasons().isEmpty()) {
            logger.debug(proc + "40");

            final TeamGender genderType = personRepository.getTeamGender(personId, Role.ATHLETE);
            if (genderType != null) {
               logger.debug(proc + "50");

               if (genderType.equals(TeamGender.BOYS) || genderType.equals(TeamGender.MEN)) {
                  teamGender = Gender.MALE;
               }
               else {
                  teamGender = Gender.FEMALE;
               }
               if (!teamGender.equals(gender)) {
                  errors.rejectValue("gender", "person.gender.invalid");
               }
            }
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates that the date the parent release was sent. If the parent release sent date is before
    * the received on date , or if the received on date is null, the processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkParentReleaseSentOn(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkParentReleaseSentOn.";
      String personId = null;
      LocalDate sentOn = null;
      LocalDate receivedOn = null;
      final LocalDate eot = Common.EOT.toLocalDate();

      if (target instanceof PersonResource) {
         final PersonResource r = (PersonResource)target;
         personId = r.getId();
         sentOn =
            r.getParentReleaseSentOn() == null ? null : LocalDate.parse(r.getParentReleaseSentOn());
         receivedOn =
            r.getParentReleaseReceivedOn() == null ? null : LocalDate.parse(r
               .getParentReleaseReceivedOn());
      }
      else if (target instanceof PersonForm) {
         final PersonForm form = (PersonForm)target;
         personId = form.getId();
         sentOn = form.getParentReleaseSentOn();
         receivedOn = form.getParentReleaseReceivedOn();
      }

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(personId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final PersonEntry person = personRepository.findOne(personId);
         if (!Common.nvl(person.getParentReleaseSentOn(), eot).equals(Common.nvl(sentOn, eot))) {
            logger.debug(proc + "40");

            if (Common.nvl(sentOn, eot).isAfter(Common.nvl(receivedOn, eot))) {
               errors.rejectValue("parentReleaseSentOn", "person.parentReleaseSentOn.invalid");
            }
         }
      }
      else {
         logger.debug(proc + "50");
         if (Common.nvl(sentOn, eot).isAfter(Common.nvl(receivedOn, eot))) {
            errors.rejectValue("parentReleaseSentOn", "person.parentReleaseSentOn.invalid");
         }
      }
      logger.debug("Leaving: " + proc + "60");
   }

   /**
    * Validates the date the parent release was received. If the parent release received date is
    * after the sent date, the processing continues.
    *
    * @param form
    * @param errors
    */
   private void checkParentReleaseReceivedOn(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkParentReleaseReceivedOn.";
      String personId = null;
      LocalDate sentOn = null;
      LocalDate receivedOn = null;
      final LocalDate eot = Common.EOT.toLocalDate();

      if (target instanceof PersonResource) {
         final PersonResource r = (PersonResource)target;
         personId = r.getId();
         sentOn =
            r.getParentReleaseSentOn() == null ? null : LocalDate.parse(r.getParentReleaseSentOn());
         receivedOn =
            r.getParentReleaseReceivedOn() == null ? null : LocalDate.parse(r
               .getParentReleaseReceivedOn());
      }
      else if (target instanceof PersonForm) {
         final PersonForm form = (PersonForm)target;
         personId = form.getId();
         sentOn = form.getParentReleaseSentOn();
         receivedOn = form.getParentReleaseReceivedOn();
      }

      logger.debug("Entering: " + proc + "10");

      if (receivedOn != null) {
         logger.debug(proc + "20");

         if (sentOn == null) {
            errors.rejectValue("parentReleaseSentOn", "person.parentReleaseSentOn.required");
         }

         final boolean isUpdating = apiUpdating(personId);
         logger.debug(proc + "30");

         if (isUpdating) {
            logger.debug(proc + "40");

            final PersonEntry person = personRepository.findOne(personId);
            if (!Common.nvl(person.getParentReleaseReceivedOn(), eot).equals(
               Common.nvl(receivedOn, eot))) {
               logger.debug(proc + "50");

               if (Common.nvl(receivedOn, eot).isBefore(Common.nvl(sentOn, eot))) {
                  errors.rejectValue("parentReleaseReceivedOn",
                     "person.parentReleaseReceivedOn.invalid");
               }
            }

         }
         else {
            logger.debug(proc + "60");
            if (Common.nvl(receivedOn, eot).isBefore(Common.nvl(sentOn, eot))) {
               errors.rejectValue("parentReleaseReceivedOn",
                  "person.parentReleaseReceivedOn.invalid");
            }
         }
      }
      logger.debug("Leaving: " + proc + "70");
   }

   /**
    * Returns true if the record with the given primary key is being updated, false otherwise.
    *
    * @param personId
    * @return
    * @throws IllegalStateException if no record exists with the given primary key.
    */
   private boolean apiUpdating(String personId) {
      boolean result = false;
      if (personId == null) {
         result = false;
      }
      else {
         final boolean exists = personRepository.exists(personId);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
