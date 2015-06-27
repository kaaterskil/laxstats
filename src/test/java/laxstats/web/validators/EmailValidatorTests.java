package laxstats.web.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EmailValidatorTests {

   Validator validator;

   @Before
   public void setUp() {
      validator = EmailValidator.getInstance();
   }

   @Test
   public void testValidEmail() {
      final String email = "blair@kaaterskil.com";
      final boolean isValid = validator.isValid(email);
      assertTrue(isValid);
   }

   @Test
   public void testInvalidAccountName() {
      final String email = "foo.com";
      final boolean isValid = validator.isValid(email);
      assertFalse(isValid);
   }

   @Test
   public void testMultipleAtSigns() {
      final String email = "blair@foo@foo.com";
      final boolean isValid = validator.isValid(email);
      assertFalse(isValid);
   }

   @Test
   public void testIpAddress() {
      final String email = "blair@192.168.1.1";
      final boolean isValid = validator.isValid(email);
      assertTrue(isValid);
   }
}
