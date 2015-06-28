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
public class PersonFormValidatorTests {

   @Mock
   PersonQueryRepository personQueryRepository;

   @InjectMocks
   PersonFormValidator validator = new PersonFormValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(PersonForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void personLastNameIsMissing() {
      final PersonForm form = TestUtils.newPersonForm();
      form.setLastName(null);

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Person Validation Tests ----------*/

   @Test
   public void newPersonIsValid() {
      final PersonForm form = TestUtils.newPersonForm();

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newPersonHasInvalidBirthday() {
      final LocalDate someFutureDate = LocalDate.now().plusYears(2);
      final PersonForm form = TestUtils.newPersonForm();
      form.setBirthdate(someFutureDate);

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newPersonHasInvalidReleaseSentDate() {
      final PersonForm form = TestUtils.newPersonForm();
      form.setParentReleaseSentOn(null);

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newPersonHasInvalidReleaseReceivedDate() {
      final PersonForm form = TestUtils.newPersonForm();
      form.setParentReleaseReceivedOn(form.getParentReleaseSentOn().minusDays(12));

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Person Validation Tests ----------*/

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
