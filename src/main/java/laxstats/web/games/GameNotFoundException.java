package laxstats.web.games;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a game resource with the given identifier cannot be found.
 */
public class GameNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = -6269408311300126191L;

   /**
    * Creates a {@code GameNotFoundException} with the given identifier.
    * 
    * @param message
    */
   public GameNotFoundException(String id) {
      super("Game not found with id: " + id);
   }

}
