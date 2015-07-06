package laxstats.web.games;

import laxstats.api.games.AthleteStatus;
import laxstats.api.players.Role;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.AttendeeQueryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AttendeeValidator implements Validator {
   private static final Logger logger = LoggerFactory.getLogger(AttendeeValidator.class);
   private static final String PACKAGE_NAME = AttendeeValidator.class.getPackage().getName();

   @Autowired
   private AttendeeQueryRepository attendeeRepository;

   @Override
   public boolean supports(Class<?> clazz) {
      return AttendeeResource.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      final String proc = PACKAGE_NAME + ".validate.";
      final AttendeeResource resource = (AttendeeResource)target;

      logger.debug("Entering: " + proc + "10");

      // Validate mandatory args
      checkMandatoryArgs(resource, errors);
      logger.debug(proc + "20");

      // Validate non-updateable args
      checkNonUpdateableArgs(resource, errors);
      logger.debug("Leaving: " + proc + "30");
   }

   /**
    * Validates that mandatory arguments have been set.
    *
    * @param target
    * @param errors
    */
   private void checkMandatoryArgs(AttendeeResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final Role role = target.getRole();
      final String playerId = target.getPlayerId();
      final AthleteStatus status = target.getStatus();

      logger.debug("Entering: " + proc + "10");

      if (role == null) {
         errors.reject("role", "attendee.role.required");
      }
      logger.debug(proc + "20");

      if (playerId == null) {
         errors.reject("playerId", "attendee.player.required");
      }
      logger.debug(proc + "30");

      if (role.equals(Role.ATHLETE)) {
         logger.debug(proc + "40");
         if (status == null) {
            errors.reject("status", "attendee.status.required");
         }
      }
      logger.debug("Leaving: " + proc + "50");
   }

   /**
    * Validates non-updateable arguments. If the given player has not changed, then processing
    * continues.
    *
    * @param target
    * @param errors
    */
   private void checkNonUpdateableArgs(AttendeeResource target, Errors errors) {
      final String proc = PACKAGE_NAME + ".checkMandatoryArgs.";
      final String attendeeId = target.getId();
      final String playerId = target.getPlayerId();

      logger.debug("Entering: " + proc + "10");

      final boolean isUpdating = apiUpdating(attendeeId);
      logger.debug(proc + "20");

      if (isUpdating) {
         logger.debug(proc + "30");

         final AttendeeEntry oldAttendee = attendeeRepository.findOne(attendeeId);
         if (!oldAttendee.getPlayer().getId().equals(playerId)) {
            errors.rejectValue("playerId", "attendee.player.nonUpdateable");
         }
      }
      logger.debug("Leaving: " + proc + "40");
   }

   /**
    * Returns true if the record with the given primary key is being updated, false otherwise.
    *
    * @param attendeeId
    * @return
    * @throws IllegalStateException if no record exists with the given primary key.
    */
   private boolean apiUpdating(String attendeeId) {
      boolean result = false;
      if (attendeeId == null) {
         result = false;
      }
      else {
         final boolean exists = attendeeRepository.exists(attendeeId);
         if (!exists) {
            throw new IllegalStateException("Invalid primary key");
         }
         result = true;
      }
      return result;
   }
}
