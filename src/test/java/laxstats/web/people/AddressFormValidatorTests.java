package laxstats.web.people;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.people.ZipCodeQueryRepository;
import laxstats.web.validators.PostalCodeValidator;

import org.axonframework.domain.IdentifierFactory;
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
public class AddressFormValidatorTests {

   @Mock
   PersonQueryRepository personQueryRepository;
   @Mock
   ZipCodeQueryRepository zipCodeQueryRepository;

   @InjectMocks
   PostalCodeValidator postalCodeValidator;
   @InjectMocks
   AddressFormValidator validator = new AddressFormValidator();

   @Before
   public void setUp() {
      validator.setPostalCodeValidator(postalCodeValidator);
   }

   @Test
   public void supports() {
      assertTrue(validator.supports(AddressForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void newAddressIsValid() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressForm form = TestUtils.newAddressForm();
      form.setPersonId(personId);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newAddressHasNoType() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressForm form = TestUtils.newAddressForm();
      form.setType(null);
      form.setPersonId(personId);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newAddressHasNoCity() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressForm form = TestUtils.newAddressForm();
      form.setCity(null);
      form.setPersonId(personId);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newDuplicateAddress() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      final AddressForm form = TestUtils.newAddressForm();
      form.setPersonId(personId);

      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(
         TestUtils.getPersonWithPrimaryAddress());
      Mockito.when(
         personQueryRepository.uniqueAddress(form.getType(), form.getAddress1(), form.getCity(),
            form.getRegion(), form.getPersonId())).thenReturn(1);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newAddressButPrimaryAlreadyAssigned() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      final AddressForm form = TestUtils.newAddressForm();
      form.setAddress1("123 Main Street");
      form.setPersonId(personId);

      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(
         TestUtils.getPersonWithPrimaryAddress());
      Mockito.when(
         personQueryRepository.uniqueAddress(form.getType(), form.getAddress1(), form.getCity(),
            form.getRegion(), form.getPersonId())).thenReturn(0);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newAddressHasInvalidPostalCode() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressForm form = TestUtils.newAddressForm();
      form.setPersonId(personId);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
