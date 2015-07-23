package laxstats.web.people;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a person with the given identifier cannot be found.
 */
public class PersonNotFoundException extends ResourceNotFoundException {

   private static final long serialVersionUID = 4433863505068723594L;

   /**
    * Creates a {@code PersonNotFoundException}.
    * 
    * @param id
    */
   public PersonNotFoundException(String id) {
      super("Person not found with id: " + id);
   }

}
