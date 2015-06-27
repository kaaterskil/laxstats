package laxstats.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import laxstats.query.people.ZipCodeQueryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@code PostalCodeValidator} is a validator that verifies that a user-defined postal code matches
 * the primary key of a persistent {@code ZipCode}. Null postal codes are considered valid.
 */
@Service
public class PostalCodeValidator implements Validator {
   private static String US_ZIP_CODE = "\\d{5}(?:[-\\s]\\d{4})?";

   @Autowired
   private ZipCodeQueryRepository repository;

   private Pattern pattern;

   public PostalCodeValidator() {
      initialize();
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
