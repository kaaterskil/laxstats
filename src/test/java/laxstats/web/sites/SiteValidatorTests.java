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
public class SiteValidatorTests {

   @Mock
   SiteQueryRepository siteQueryRepository;
   @Mock
   ZipCodeQueryRepository zipCodeQueryRespository;

   @InjectMocks
   PostalCodeValidator postalCodeValidator;
   @InjectMocks
   SiteValidator validator = new SiteValidator();

   @Before
   public void setUp() {
      validator.setPostalCodeValidator(postalCodeValidator);
   }

   @Test
   public void supports() {
      assertTrue(validator.supports(SiteResourceImpl.class));
      assertTrue(validator.supports(SiteForm.class));
      assertFalse(validator.supports(Object.class));
   }

   /*---------- New Site Tests ----------*/

   @Test
   public void newSiteResourceIsValid() {
      final SiteResourceImpl resource = TestUtils.newSiteResource();

      Mockito.when(zipCodeQueryRespository.exists(resource.getPostalCode()))
         .thenReturn(true);

      final BindException errors = new BindException(resource, "siteResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSiteIsValid() {
      final SiteForm form = TestUtils.newSiteForm();

      Mockito.when(zipCodeQueryRespository.exists(form.getPostalCode()))
         .thenReturn(true);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSiteResourceMissingName() {
      final SiteResourceImpl resource = TestUtils.newSiteResource();
      resource.setName(null);

      Mockito.when(zipCodeQueryRespository.exists(resource.getPostalCode()))
         .thenReturn(true);

      final BindException errors = new BindException(resource, "siteResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteMissingName() {
      final SiteForm form = TestUtils.newSiteForm();
      form.setName(null);

      Mockito.when(zipCodeQueryRespository.exists(form.getPostalCode()))
         .thenReturn(true);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteResourceMissingCity() {
      final SiteResourceImpl resource = TestUtils.newSiteResource();
      resource.setCity(null);

      Mockito.when(zipCodeQueryRespository.exists(resource.getPostalCode()))
         .thenReturn(true);

      final BindException errors = new BindException(resource, "siteResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteMissingCity() {
      final SiteForm form = TestUtils.newSiteForm();
      form.setCity(null);

      Mockito.when(zipCodeQueryRespository.exists(form.getPostalCode()))
         .thenReturn(true);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteResourceMissingRegion() {
      final SiteResourceImpl resource = TestUtils.newSiteResource();
      resource.setRegion(null);

      Mockito.when(zipCodeQueryRespository.exists(resource.getPostalCode()))
         .thenReturn(true);

      final BindException errors = new BindException(resource, "siteResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteMissingRegion() {
      final SiteForm form = TestUtils.newSiteForm();
      form.setRegion(null);

      Mockito.when(zipCodeQueryRespository.exists(form.getPostalCode()))
         .thenReturn(true);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newSiteResourceMissingPostalCode() {
      final SiteResourceImpl resource = TestUtils.newSiteResource();
      resource.setPostalCode(null);

      final BindException errors = new BindException(resource, "siteResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newSiteResourceHasDuplicateName() {
      final SiteResourceImpl resource = TestUtils.newSiteResource();

      Mockito.when(
         siteQueryRepository.uniqueName(resource.getName(), resource.getCity(), resource.getRegion()))
         .thenReturn(1);

      final BindException errors = new BindException(resource, "siteResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
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
   public void updateSiteResourceDuplicateName() {
      final SiteEntry site = TestUtils.getExistingSite();
      final String id = site.getId();

      final SiteResourceImpl resource = TestUtils.newSiteResource();
      resource.setId(id);
      resource.setName("This is a duplicate name");

      Mockito.when(siteQueryRepository.exists(id))
         .thenReturn(true);
      Mockito.when(siteQueryRepository.findOne(id))
         .thenReturn(TestUtils.getExistingSite());
      Mockito.when(
         siteQueryRepository.updateName(resource.getName(), resource.getCity(),
            resource.getRegion(), id))
         .thenReturn(1);

      final BindException errors = new BindException(resource, "siteResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateSiteDuplicateName() {
      final SiteEntry site = TestUtils.getExistingSite();
      final String id = site.getId();

      final SiteForm form = TestUtils.newSiteForm();
      form.setId(id);
      form.setName("This is a duplicate name");

      Mockito.when(siteQueryRepository.exists(id))
         .thenReturn(true);
      Mockito.when(siteQueryRepository.findOne(id))
         .thenReturn(TestUtils.getExistingSite());
      Mockito.when(
         siteQueryRepository.updateName(form.getName(), form.getCity(), form.getRegion(), id))
         .thenReturn(1);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateSiteResourceInvalidPostalCode() {
      final SiteEntry site = TestUtils.getExistingSite();
      final String id = site.getId();

      final SiteResourceImpl resource = TestUtils.newSiteResource();
      resource.setId(id);
      resource.setPostalCode("000");

      Mockito.when(siteQueryRepository.exists(id))
         .thenReturn(true);
      Mockito.when(siteQueryRepository.findOne(id))
         .thenReturn(TestUtils.getExistingSite());
      Mockito.when(
         siteQueryRepository.updateName(resource.getName(), resource.getCity(),
            resource.getRegion(), id))
         .thenReturn(0);

      final BindException errors = new BindException(resource, "siteResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());

   }

   @Test
   public void updateSiteInvalidPostalCode() {
      final SiteEntry site = TestUtils.getExistingSite();
      final String id = site.getId();

      final SiteForm form = TestUtils.newSiteForm();
      form.setId(id);
      form.setPostalCode("000");

      Mockito.when(siteQueryRepository.exists(id))
         .thenReturn(true);
      Mockito.when(siteQueryRepository.findOne(id))
         .thenReturn(TestUtils.getExistingSite());
      Mockito.when(
         siteQueryRepository.updateName(form.getName(), form.getCity(), form.getRegion(), id))
         .thenReturn(0);

      final BindException errors = new BindException(form, "siteForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

   }
}
