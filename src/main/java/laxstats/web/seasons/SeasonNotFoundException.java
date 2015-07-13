package laxstats.web.seasons;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a season resource is requested by a remote client with an invalid primary key.
 */
public class SeasonNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = -8097470005741530945L;

   public SeasonNotFoundException(String id) {
      super("Season not found with id=" + id);
   }
}
