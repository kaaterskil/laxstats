package laxstats.web.players;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a player with the given identifier cannot be found.
 */
public class PlayerNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = -4018343377770206824L;

   /**
    * Creates a {@code PlayerNotFoundException} with the given identifier.
    * 
    * @param message
    */
   public PlayerNotFoundException(String id) {
      super("Player not found with id: " + id);
   }

}
