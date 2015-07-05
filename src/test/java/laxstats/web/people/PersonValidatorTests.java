package laxstats.web.people;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

@RunWith(MockitoJUnitRunner.class)
public class PersonValidatorTests {

   @Mock
   PersonQueryRepository personQueryRepository;

   @InjectMocks
   PersonValidator validator = new PersonValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(PersonResource.class));
      assertTrue(validator.supports(PersonForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void personLastNameIsMissing() {
      final PersonForm form = TestUtils.newPersonForm();
      form.setLastName(null);

      final PersonResource resource = TestUtils.newPersonResource();
      resource.setLastName(null);

      BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Person Validation Tests ----------*/

   @Test
   public void newPersonIsValid() {
      final PersonForm form = TestUtils.newPersonForm();
      final PersonResource resource = TestUtils.newPersonResource();

      BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());

      errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newPersonHasInvalidBirthday() {
      final LocalDate someFutureDate = LocalDate.now().plusYears(2);

      final PersonForm form = TestUtils.newPersonForm();
      form.setBirthdate(someFutureDate);

      final PersonResource resource = TestUtils.newPersonResource();
      resource.setBirthdate(someFutureDate.toString("yyyy-MM-dd"));

      BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newPersonHasInvalidReleaseSentDate() {
      final PersonForm form = TestUtils.newPersonForm();
      form.setParentReleaseSentOn(null);

      final PersonResource resource = TestUtils.newPersonResource();
      resource.setParentReleaseSentOn(null);

      BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newPersonHasInvalidReleaseReceivedDate() {
      final PersonForm form = TestUtils.newPersonForm();
      final LocalDate someInvalidDate = form.getParentReleaseSentOn().minusDays(12);
      form.setParentReleaseReceivedOn(someInvalidDate);

      final PersonResource resource = TestUtils.newPersonResource();
      resource.setParentReleaseReceivedOn(someInvalidDate.toString("yyyy-MM-dd"));

      BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());

      errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Person Validation Tests ----------*/

   @Test
   public void existingPersonResourceHasInvalidBirthday() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final String someFutureDate = LocalDate.now().plusYears(2).toString("yyyy-MM-dd");
      final PersonResource resource = TestUtils.newPersonResource();
      resource.setId(person.getId());
      resource.setBirthdate(someFutureDate);

      Mockito.when(personQueryRepository.exists(resource.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(resource.getId())).thenReturn(person);

      final BindException errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingPersonHasInvalidBirthday() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final LocalDate someFutureDate = LocalDate.now().plusYears(2);
      final PersonForm form = TestUtils.newPersonForm();
      form.setId(person.getId());
      form.setBirthdate(someFutureDate);

      Mockito.when(personQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(form.getId())).thenReturn(person);

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingPersonResourceHasInvalidReleaseSentDate() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final PersonResource resource = TestUtils.newPersonResource();
      resource.setId(person.getId());
      resource.setParentReleaseSentOn(null);

      Mockito.when(personQueryRepository.exists(resource.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(resource.getId())).thenReturn(person);

      final BindException errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingPersonHasInvalidReleaseSentDate() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final PersonForm form = TestUtils.newPersonForm();
      form.setId(person.getId());
      form.setParentReleaseSentOn(null);

      Mockito.when(personQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(form.getId())).thenReturn(person);

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingPersonResourceHasInvalidReleaseReceivedDate() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final PersonResource resource = TestUtils.newPersonResource();
      final String someInvalidDate =
         LocalDate.parse(resource.getParentReleaseSentOn()).minusDays(1).toString("yyyy-MM-dd");

      resource.setId(person.getId());
      resource.setParentReleaseReceivedOn(someInvalidDate);

      Mockito.when(personQueryRepository.exists(resource.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(resource.getId())).thenReturn(person);

      final BindException errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingPersonHasInvalidReleaseReceivedDate() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final PersonForm form = TestUtils.newPersonForm();
      form.setId(person.getId());
      form.setParentReleaseReceivedOn(form.getParentReleaseSentOn().minusDays(1));

      Mockito.when(personQueryRepository.exists(form.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(form.getId())).thenReturn(person);

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
