package laxstats.web.people;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.api.people.AddressType;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.PersonEntry;
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
public class AddressValidatorTests {

   @Mock
   PersonQueryRepository personQueryRepository;
   @Mock
   ZipCodeQueryRepository zipCodeQueryRepository;

   @InjectMocks
   PostalCodeValidator postalCodeValidator;
   @InjectMocks
   AddressValidator validator = new AddressValidator();

   @Before
   public void setUp() {
      validator.setPostalCodeValidator(postalCodeValidator);
   }

   @Test
   public void supports() {
      assertTrue(validator.supports(AddressResourceImpl.class));
      assertTrue(validator.supports(AddressForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void addressResourceHasNoType() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setType(null);
      resource.setPersonId(personId);

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void addressHasNoType() {
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
   public void addressResourceHasNoCity() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setCity(null);
      resource.setPersonId(personId);

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void addressHasNoCity() {
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

   /*---------- New Address Tests ----------*/

   @Test
   public void newAddressResourceIsValid() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setPersonId(personId);

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
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
   public void newResourceDuplicateAddress() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setPersonId(personId);

      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());
      Mockito.when(
         personQueryRepository.uniqueAddress(resource.getType(), resource.getAddress1(), resource
            .getCity(), resource.getRegion(), resource.getPersonId())).thenReturn(1);
      Mockito.when(postalCodeValidator.isValid(resource.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newDuplicateAddress() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      final AddressForm form = TestUtils.newAddressForm();
      form.setPersonId(personId);

      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());
      Mockito.when(
         personQueryRepository.uniqueAddress(form.getType(), form.getAddress1(), form.getCity(),
            form.getRegion(), form.getPersonId())).thenReturn(1);
      Mockito.when(postalCodeValidator.isValid(form.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newAddressResourceButPrimaryAlreadyAssigned() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setAddress1("123 Main Street");
      resource.setPersonId(personId);

      Mockito.when(zipCodeQueryRepository.exists("01776")).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(
         TestUtils.getPersonWithPrimaryAddress());
      Mockito.when(
         personQueryRepository.uniqueAddress(resource.getType(), resource.getAddress1(), resource
            .getCity(), resource.getRegion(), resource.getPersonId())).thenReturn(0);

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newAddressResourceHasInvalidPostalCode() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setPersonId(personId);
      resource.setPostalCode("0000");

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newAddressHasInvalidPostalCode() {
      final String personId = IdentifierFactory.getInstance().generateIdentifier();
      Mockito.when(personQueryRepository.findOne(personId)).thenReturn(TestUtils.getPerson());

      final AddressForm form = TestUtils.newAddressForm();
      form.setPersonId(personId);
      form.setPostalCode("0000");

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Address Tests ----------*/

   @Test
   public void updateAddressResourceWithDuplicate() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryAddress();
      final String addressId = person.getAddresses().get(0).getId();

      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setId(addressId);
      resource.setAddress1("This is a duplicate address");
      resource.setPrimary(false);
      resource.setPersonId(person.getId());

      Mockito.when(personQueryRepository.checkAddressExists(addressId)).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(person.getId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateAddress(resource.getType(), resource.getAddress1(), resource
            .getCity(), resource.getRegion(), resource.getPersonId(), resource.getId())).thenReturn(
         1);
      Mockito.when(postalCodeValidator.isValid(resource.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateAddressWithDuplicate() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryAddress();
      final String addressId = person.getAddresses().get(0).getId();

      final AddressForm form = TestUtils.newAddressForm();
      form.setId(addressId);
      form.setAddress1("This is a duplicate address");
      form.setPrimary(false);
      form.setPersonId(person.getId());

      Mockito.when(personQueryRepository.checkAddressExists(addressId)).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(person.getId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateAddress(form.getType(), form.getAddress1(), form.getCity(),
            form.getRegion(), form.getPersonId(), form.getId())).thenReturn(1);
      Mockito.when(postalCodeValidator.isValid(form.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateAddressResourceButPrimaryAlreadyAssigned() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryAddress();

      final String addressId = IdentifierFactory.getInstance().generateIdentifier();
      final AddressEntry secondAddress = TestUtils.getHomeAddress();
      secondAddress.setId(addressId);
      secondAddress.setPrimary(false);
      secondAddress.setAddressType(AddressType.VACATION);
      person.addAddress(secondAddress);

      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setId(addressId);
      resource.setPrimary(true);
      resource.setType(AddressType.VACATION);
      resource.setPersonId(person.getId());

      Mockito.when(personQueryRepository.checkAddressExists(addressId)).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(person.getId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateAddress(resource.getType(), resource.getAddress1(), resource
            .getCity(), resource.getRegion(), resource.getPersonId(), resource.getId())).thenReturn(
         0);
      Mockito.when(postalCodeValidator.isValid(resource.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateAddressButPrimaryAlreadyAssigned() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryAddress();

      final String addressId = IdentifierFactory.getInstance().generateIdentifier();
      final AddressEntry secondAddress = TestUtils.getHomeAddress();
      secondAddress.setId(addressId);
      secondAddress.setPrimary(false);
      secondAddress.setAddressType(AddressType.VACATION);
      person.addAddress(secondAddress);

      final AddressForm form = TestUtils.newAddressForm();
      form.setId(addressId);
      form.setPrimary(true);
      form.setType(AddressType.VACATION);
      form.setPersonId(person.getId());

      Mockito.when(personQueryRepository.checkAddressExists(addressId)).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(person.getId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateAddress(form.getType(), form.getAddress1(), form.getCity(),
            form.getRegion(), form.getPersonId(), form.getId())).thenReturn(0);
      Mockito.when(postalCodeValidator.isValid(form.getPostalCode())).thenReturn(true);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateAddressResourceWithInvalidPostalCode() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryAddress();
      final String addressId = person.getAddresses().get(0).getId();

      final AddressResourceImpl resource = TestUtils.newAddressResource();
      resource.setId(addressId);
      resource.setPrimary(false);
      resource.setPersonId(person.getId());

      Mockito.when(personQueryRepository.checkAddressExists(addressId)).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(person.getId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateAddress(resource.getType(), resource.getAddress1(), resource
            .getCity(), resource.getRegion(), resource.getPersonId(), resource.getId())).thenReturn(
         0);
      Mockito.when(postalCodeValidator.isValid(resource.getPostalCode())).thenReturn(false);

      final BindException errors = new BindException(resource, "addressResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateAddressWithInvalidPostalCode() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryAddress();
      final String addressId = person.getAddresses().get(0).getId();

      final AddressForm form = TestUtils.newAddressForm();
      form.setId(addressId);
      form.setPrimary(false);
      form.setPersonId(person.getId());

      Mockito.when(personQueryRepository.checkAddressExists(addressId)).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(person.getId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateAddress(form.getType(), form.getAddress1(), form.getCity(),
            form.getRegion(), form.getPersonId(), form.getId())).thenReturn(0);
      Mockito.when(postalCodeValidator.isValid(form.getPostalCode())).thenReturn(false);

      final BindException errors = new BindException(form, "addressForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
