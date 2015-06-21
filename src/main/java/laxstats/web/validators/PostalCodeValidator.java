package laxstats.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import laxstats.query.people.ZipCodeQueryRepository;

public class PostalCodeValidator implements Validator {
   private static String US_ZIP_CODE = "\\d{5}(?:[-\\s]\\d{4})?";

   private static Validator INSTANCE = new PostalCodeValidator();

   public static Validator getInstance() {
      return INSTANCE;
   }

   @Autowired
   private ZipCodeQueryRepository repository;

   private Pattern pattern;

   protected PostalCodeValidator() {
      initialize();
   }

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
         final String zipCode = input.substring(0, 4);
         return repository.exists(zipCode);
      }
      return false;
   }

   private void initialize() {
      pattern = Pattern.compile("^" + US_ZIP_CODE + "?");
   }
}
