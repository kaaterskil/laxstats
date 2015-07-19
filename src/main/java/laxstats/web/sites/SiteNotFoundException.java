package laxstats.web.sites;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when the playing field resource with the given identifier cannot be found.
 */
public class SiteNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = -3902399869005777770L;

   /**
    * Creates a {@code SiteNotFoundException} with the given identifier.
    *
    * @param id
    */
   public SiteNotFoundException(String id) {
      super("Playing field not found with id: " + id);
   }

}
