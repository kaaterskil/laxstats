package laxstats.web.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import laxstats.Application;
import laxstats.query.people.ZipCodeQueryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ZipCodeValidatorTests {

   @Autowired
   ZipCodeQueryRepository repository;

   PostalCodeValidator validator;

   @Before
   public void setUp() {
      validator = (PostalCodeValidator)PostalCodeValidator.getInstance();
      validator.setRepository(repository);
   }

   @Test
   public void test5DigitZipCode() {
      final String zipCode = "01776";
      final boolean isValid = validator.isValid(zipCode);
      Assert.isTrue(isValid);
   }

   @Test
   public void test9DigitZipCode() {
      final String zipCode = "01776-4616";
      final boolean isValid = validator.isValid(zipCode);
      Assert.isTrue(isValid);
   }

   @Test
   public void testTooLongZipCode() {
      final String zipCode = "0177668";
      final boolean isValid = validator.isValid(zipCode);
      Assert.isTrue(!isValid);
   }
}
