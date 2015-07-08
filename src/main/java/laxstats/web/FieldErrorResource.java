package laxstats.web;

/**
 * {@code FieldErrorResource} contains information about a validation error.
 */
public class FieldErrorResource {

   private final String field;
   private final String message;

   /**
    * Creates a {@code FieldErrorResource} with the given field name and validation error message.
    * 
    * @param field
    * @param message
    */
   public FieldErrorResource(String field, String message) {
      this.field = field;
      this.message = message;
   }

   /**
    * Returns the field name that was validated.
    * 
    * @return
    */
   public String getField() {
      return field;
   }

   /**
    * Returns the validation error message.
    * 
    * @return
    */
   public String getMessage() {
      return message;
   }
}
