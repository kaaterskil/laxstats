package laxstats.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneValidator implements Validator {
   private static final String NORTH_AMERICAN_NUMBERING_PLAN =
      "(1\\s*[-\\/\\.]?)?\\(?([2-9][0-9]{2})\\)?\\s*[-\\/\\.]?\\s*([2-9][0-9]{2})\\s*[-\\/\\.]?\\s*([0-9]{4})\\s*(([xX]|[eE][xX][tT])\\.?\\s*(\\d+))*";

   private static Validator INSTANCE = new TelephoneValidator();

   public static Validator getInstance() {
      return INSTANCE;
   }

   private Pattern pattern;

   protected TelephoneValidator() {
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
      return m.matches();
   }

   private void initialize() {
      pattern = Pattern.compile("^" + NORTH_AMERICAN_NUMBERING_PLAN + "$");
   }
}
