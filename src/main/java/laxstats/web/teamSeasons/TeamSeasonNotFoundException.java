package laxstats.web.teamSeasons;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a client requests a team season with the given identifier that cannot be found.
 */
public class TeamSeasonNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = 1954523110109609720L;

   /**
    * Creates a {@code TeamSeasonNotFoundExeption} with the given identifier.
    * 
    * @param id
    */
   public TeamSeasonNotFoundException(String id) {
      super("Team season not found with id: " + id);
   }

}
