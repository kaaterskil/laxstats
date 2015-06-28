package laxstats.web.sites;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.people.ZipCodeQueryRepository;
import laxstats.query.sites.SiteEntry;
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

   /*---------- New Site Tests ----------*/

   @Test
   public void newSiteIsValid() {
      final SiteForm form = TestUtils.newSiteForm();

      Mockito.when(zipCodeQueryRespository.exists(form.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSiteMissingName() {
      final SiteForm form = TestUtils.newSiteForm();
      form.setName(null);

      Mockito.when(zipCodeQueryRespository.exists(form.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteMissingCity() {
      final SiteForm form = TestUtils.newSiteForm();
      form.setCity(null);

      Mockito.when(zipCodeQueryRespository.exists(form.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteMissingRegion() {
      final SiteForm form = TestUtils.newSiteForm();
      form.setRegion(null);

      Mockito.when(zipCodeQueryRespository.exists(form.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteMissingPostalCode() {
      final SiteForm form = TestUtils.newSiteForm();
      form.setPostalCode(null);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSiteHasDuplicateName() {
      final SiteForm form = TestUtils.newSiteForm();

      Mockito.when(siteQueryRepository.uniqueName(form.getName(), form.getCity(), form.getRegion()))
      .thenReturn(1);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteInvalidPostalCode() {
      final SiteForm form = TestUtils.newSiteForm();

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Site Tests ----------*/

   @Test
   public void updateSiteDuplicateName() {
      final SiteEntry site = TestUtils.getExistingSite();
      final String id = site.getId();

      final SiteForm form = TestUtils.newSiteForm();
      form.setId(id);
      form.setName("This is a duplicate name");

      Mockito.when(siteQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(siteQueryRepository.findOne(id)).thenReturn(TestUtils.getExistingSite());
      Mockito.when(
         siteQueryRepository.updateName(form.getName(), form.getCity(), form.getRegion(), id))
         .thenReturn(1);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateSiteInvalidPostalCode() {
      final SiteEntry site = TestUtils.getExistingSite();
      final String id = site.getId();

      final SiteForm form = TestUtils.newSiteForm();
      form.setId(id);
      form.setPostalCode("000");

      Mockito.when(siteQueryRepository.exists(id)).thenReturn(true);
      Mockito.when(siteQueryRepository.findOne(id)).thenReturn(TestUtils.getExistingSite());
      Mockito.when(
         siteQueryRepository.updateName(form.getName(), form.getCity(), form.getRegion(), id))
         .thenReturn(0);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

   }
}
