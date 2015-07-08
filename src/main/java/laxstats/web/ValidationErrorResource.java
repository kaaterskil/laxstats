package laxstats.web;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ValidationErrorResource} contains a list of field validation errors.
 */
public class ValidationErrorResource {

   private final List<FieldErrorResource> fieldErrors = new ArrayList<>();

   /**
    * Creates a new {@code ValidationErrorResource}.
    */
   public ValidationErrorResource() {
   }

   /**
    * Creates a {@code FieldErrorResource} from the given field name and validation error message
    * and adds it to the list.
    *
    * @param path
    * @param message
    */
   public void addFieldError(String path, String message) {
      final FieldErrorResource error = new FieldErrorResource(path, message);
      fieldErrors.add(error);
   }

   /**
    * Returns a list of {@code FieldErrorResource} objects.
    * 
    * @return
    */
   public List<FieldErrorResource> getFieldErrors() {
      return fieldErrors;
   }
}
