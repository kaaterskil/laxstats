package laxstats.web.validators;

import laxstats.query.people.ZipCodeQueryRepository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZipCodeValidatorTests {

   @Mock
   ZipCodeQueryRepository repository;

   @InjectMocks
   PostalCodeValidator validator = new PostalCodeValidator();

   @Test
   public void test5DigitZipCode() {
      Mockito.when(repository.exists("01776")).thenReturn(true);

      final String zipCode = "01776";
      final boolean isValid = validator.isValid(zipCode);
      Assert.assertTrue(isValid);
   }

   @Test
   public void test9DigitZipCode() {
      Mockito.when(repository.exists("01776")).thenReturn(true);

      final String zipCode = "01776-4616";
      final boolean isValid = validator.isValid(zipCode);
      Assert.assertTrue(isValid);
   }

   @Test
   public void testTooLongZipCode() {
      Mockito.when(repository.exists("01776")).thenReturn(true);

      final String zipCode = "0177668";
      final boolean isValid = validator.isValid(zipCode);
      Assert.assertFalse(isValid);
   }
}
