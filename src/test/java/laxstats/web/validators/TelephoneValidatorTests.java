package laxstats.web.validators;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class TelephoneValidatorTests {

   Validator validator;

   @Before
   public void setUp() {
      validator = TelephoneValidator.getInstance();
   }

   @Test
   public void test7DigitNumber() {
      final String input = "788-1974";
      final boolean isValid = validator.isValid(input);
      Assert.isTrue(!isValid);
   }

   @Test
   public void testNumberWithAreaCode() {
      final String input = "339-788-1974";
      final boolean isValid = validator.isValid(input);
      Assert.isTrue(isValid);
   }

   @Test
   public void testNumberWithAreaCodeInParentheses() {
      final String input = "(339) 788-1974";
      final boolean isValid = validator.isValid(input);
      Assert.isTrue(isValid);
   }

   @Test
   public void testNoHyphenatedNumber() {
      final String input = "3397881974";
      final boolean isValid = validator.isValid(input);
      Assert.isTrue(isValid);
   }

   @Test
   public void testShortNumber() {
      final String input = "788-197";
      final boolean isValid = validator.isValid(input);
      Assert.isTrue(!isValid);
   }

   @Test
   public void testLongNumber() {
      final String input = "33978819742";
      final boolean isValid = validator.isValid(input);
      Assert.isTrue(!isValid);
   }
}
