package laxstats.web.sites;

import laxstats.api.Region;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.query.people.ZipCodeQueryRepository;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.web.validators.PostalCodeValidator;

import org.junit.Assert;
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
      Assert.assertTrue(validator.supports(SiteForm.class));
      Assert.assertFalse(validator.supports(Object.class));
   }

   @Test
   public void newSiteIsValid() {
      Mockito.when(zipCodeQueryRespository.exists("01776")).thenReturn(true);

      final SiteForm form = getForm();
      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertFalse(errors.hasErrors());
   }

   @Test
   public void newSiteHasNoName() {
      Mockito.when(zipCodeQueryRespository.exists("01776")).thenReturn(true);

      final SiteForm form = getForm();
      form.setName(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteHasNoCity() {
      Mockito.when(zipCodeQueryRespository.exists("01776")).thenReturn(true);

      final SiteForm form = getForm();
      form.setCity(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteHasNoRegion() {
      Mockito.when(zipCodeQueryRespository.exists("01776")).thenReturn(true);

      final SiteForm form = getForm();
      form.setRegion(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteHasNoPostalCode() {
      final SiteForm form = getForm();
      form.setPostalCode(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      Assert.assertFalse(errors.hasErrors());
   }

   private SiteForm getForm() {
      final SiteForm form = new SiteForm();
      form.setAddress1("40 Tall Pine Drive");
      form.setAddress2("#21");
      form.setCity("Sudbury");
      form.setName("Lincoln Sudbury field");
      form.setPostalCode("01776");
      form.setRegion(Region.MA);
      form.setStyle(SiteStyle.COMPETITION);
      form.setSurface(Surface.GRASS);

      return form;
   }
}
