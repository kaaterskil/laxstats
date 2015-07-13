package laxstats.web.users;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a user resource with the given identifier cannot be found.
 */
public class UserNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = -6185843222560620120L;

   /**
    * Creates a {@code UserNotFoundException} with the given identifier.
    *
    * @param id
    */
   public UserNotFoundException(String id) {
      super("User not found with id: " + id);
   }
}
