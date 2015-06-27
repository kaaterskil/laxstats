package laxstats.web.sites;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.people.ZipCodeQueryRepository;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.web.validators.PostalCodeValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

@RunWith(MockitoJUnitRunner.class)
public class SiteFormValidatorTests {

   @Mock
   SiteQueryRepository siteQueryRepository;
   @Mock
   ZipCodeQueryRepository zipCodeQueryRespository;

   @InjectMocks
   PostalCodeValidator postalCodeValidator;
   @InjectMocks
   SiteFormValidator validator = new SiteFormValidator();

   @Before
   public void setUp() {
      validator.setPostalCodeValidator(postalCodeValidator);

   }

   @Test
   public void supports() {
      assertTrue(validator.supports(SiteForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void newSiteIsValid() {
      Mockito.when(zipCodeQueryRespository.exists("01776")).thenReturn(true);

      final SiteForm form = TestUtils.newSiteForm();
      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSiteHasNoName() {
      Mockito.when(zipCodeQueryRespository.exists("01776")).thenReturn(true);

      final SiteForm form = TestUtils.newSiteForm();
      form.setName(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteHasNoCity() {
      Mockito.when(zipCodeQueryRespository.exists("01776")).thenReturn(true);

      final SiteForm form = TestUtils.newSiteForm();
      form.setCity(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteHasNoRegion() {
      Mockito.when(zipCodeQueryRespository.exists("01776")).thenReturn(true);

      final SiteForm form = TestUtils.newSiteForm();
      form.setRegion(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteHasNoPostalCode() {
      final SiteForm form = TestUtils.newSiteForm();
      form.setPostalCode(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }
}
