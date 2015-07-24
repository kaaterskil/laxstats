package laxstats.web.people;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when an address with the given identifier cannot be found.
 */
public class AddressNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = -8750866804526439691L;

   /**
    * @Creates an {@code AddressNotFoundException}
    * @param id
    */
   public AddressNotFoundException(String id) {
      super("Address not found with identifier: " + id);
   }

}
