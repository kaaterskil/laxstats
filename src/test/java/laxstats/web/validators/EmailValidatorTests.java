package laxstats.web.validators;

import org.junit.Before;
import org.junit.Test;

import io.jsonwebtoken.lang.Assert;

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
      Assert.isTrue(isValid);
   }

   @Test
   public void testInvalidAccountName() {
      final String email = "foo.com";
      final boolean isValid = validator.isValid(email);
      Assert.isTrue(!isValid);
   }

   @Test
   public void testMultipleAtSigns() {
      final String email = "blair@foo@foo.com";
      final boolean isValid = validator.isValid(email);
      Assert.isTrue(!isValid);
   }

   @Test
   public void testIpAddress() {
      final String email = "blair@192.168.1.1";
      final boolean isValid = validator.isValid(email);
      Assert.isTrue(isValid);
   }
}
