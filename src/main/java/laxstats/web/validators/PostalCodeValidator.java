package laxstats.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import laxstats.query.people.ZipCodeQueryRepository;

/**
 * {@code PostalCodeValidator} is a validator that verifies that a user-defined postal code matches
 * the primary key of a persistent {@code ZipCode}. Null postal codes are considered valid.
 */
public class PostalCodeValidator implements Validator {
   private static String US_ZIP_CODE = "\\d{5}(?:[-\\s]\\d{4})?";

   private static Validator INSTANCE = new PostalCodeValidator();

   /**
    * Returns the singleton instance of this {@code Validator}.
    *
    * @return
    */
   public static Validator getInstance() {
      return INSTANCE;
   }

   private ZipCodeQueryRepository repository;

   private Pattern pattern;

   protected PostalCodeValidator() {
      initialize();
   }

   /**
    * Sets the postal code repository.
    *
    * @param repository
    */
   @Autowired
   public void setRepository(ZipCodeQueryRepository repository) {
      this.repository = repository;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isValid(Object value) {
      if (value == null) {
         return true;
      }
      if (!(value instanceof String)) {
         return false;
      }

      final String input = (String)value;
      if (input.length() == 0) {
         return true;
      }

      final Matcher m = pattern.matcher(input);
      if (m.matches()) {
         final String zipCode = input.substring(0, 5);
         return repository.exists(zipCode);
      }
      return false;
   }

   private void initialize() {
      pattern = Pattern.compile("^" + US_ZIP_CODE + "?");
   }
}
