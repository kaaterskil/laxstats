package laxstats.web.validators;

/**
 * {@code Validator} provides a common interface for determining the results of validating various
 * inputs.
 */
public interface Validator {

   /**
    * Returns true if the constraints on the specified input are validated, false otherwise.
    *
    * @param value
    * @return
    */
      boolean isValid(Object value);
}
