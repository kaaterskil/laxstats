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
      assertTrue(validator.supports(PersonResourceImpl.class));
      assertTrue(validator.supports(PersonForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void personLastNameIsMissing() {
      final PersonForm form = TestUtils.newPersonForm();
      form.setLastName(null);

      final PersonResourceImpl resource = TestUtils.newPersonResource();
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
      final PersonResourceImpl resource = TestUtils.newPersonResource();

      BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());

      errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newPersonHasInvalidBirthday() {
      final LocalDate someFutureDate = LocalDate.now()
         .plusYears(2);

      final PersonForm form = TestUtils.newPersonForm();
      form.setBirthdateFromLocalDate(someFutureDate);

      final PersonResourceImpl resource = TestUtils.newPersonResource();
      resource.setBirthdate(someFutureDate.toString("yyyy-MM-dd"));

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

      final String someFutureDate = LocalDate.now()
         .plusYears(2)
         .toString("yyyy-MM-dd");
      final PersonResourceImpl resource = TestUtils.newPersonResource();
      resource.setId(person.getId());
      resource.setBirthdate(someFutureDate);

      Mockito.when(personQueryRepository.exists(resource.getId()))
         .thenReturn(true);
      Mockito.when(personQueryRepository.findOne(resource.getId()))
         .thenReturn(person);

      final BindException errors = new BindException(resource, "personResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void existingPersonHasInvalidBirthday() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final LocalDate someFutureDate = LocalDate.now()
         .plusYears(2);
      final PersonForm form = TestUtils.newPersonForm();
      form.setId(person.getId());
      form.setBirthdateFromLocalDate(someFutureDate);

      Mockito.when(personQueryRepository.exists(form.getId()))
         .thenReturn(true);
      Mockito.when(personQueryRepository.findOne(form.getId()))
         .thenReturn(person);

      final BindException errors = new BindException(form, "personForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
