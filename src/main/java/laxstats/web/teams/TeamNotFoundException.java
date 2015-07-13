package laxstats.web.teams;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a team resource with the given identifier cannot be found.
 */
public class TeamNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = -7350671008879888429L;

   /**
    * Creates a {@code TeamNotFoundException} with the given identifier.
    *
    * @param id
    */
   public TeamNotFoundException(String id) {
      super("Team not found with id: " + id);
   }

}
