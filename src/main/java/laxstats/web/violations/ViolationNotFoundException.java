package laxstats.web.violations;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a violation resource is requested by a remote client with an invalid primary key.
 */
public class ViolationNotFoundException extends ResourceNotFoundException {
   private static final long serialVersionUID = 9137815451790511080L;

   public ViolationNotFoundException(String id) {
      super("Violation not found with id=" + id);
   }
}
