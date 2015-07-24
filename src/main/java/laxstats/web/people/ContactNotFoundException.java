package laxstats.web.people;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a contact with the given identifier cannot be found.
 */
public class ContactNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = -7475198148757581827L;

   /**
    * @Creates an {@code ContactNotFoundException}
    * @param id
    */
   public ContactNotFoundException(String id) {
      super("Contact not found with identifier: " + id);
   }

}
