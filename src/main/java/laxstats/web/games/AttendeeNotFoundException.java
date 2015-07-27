package laxstats.web.games;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when an attendee resource matching the given identifier cannot be found.
 */
public class AttendeeNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = 2032952742698419194L;

   /**
    * Creates an {@code AttendeeNotFoundException} with the given identifier.
    * 
    * @param message
    */
   public AttendeeNotFoundException(String id) {
      super("Attendee cannot be found with id: " + id);
   }

}
