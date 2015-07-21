package laxstats.web.people;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import laxstats.TestUtils;
import laxstats.query.people.ContactEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;

import org.axonframework.domain.IdentifierFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

@RunWith(MockitoJUnitRunner.class)
public class ContactValidatorTests {

   @Mock
   PersonQueryRepository personQueryRepository;

   @InjectMocks
   ContactValidator validator = new ContactValidator();

   @Test
   public void supports() {
      assertTrue(validator.supports(ContactResourceImpl.class));
      assertTrue(validator.supports(ContactForm.class));
      assertFalse(validator.supports(Object.class));
   }

   @Test
   public void contactResourceMissingMethod() {
      final PersonEntry person = TestUtils.getPerson();

      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());
      resource.setMethod(null);

      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void contactMissingMethod() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());
      form.setMethod(null);

      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void contactResourceMissingValue() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());
      resource.setValue("   ");

      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void contactMissingValue() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());
      form.setValue("   ");

      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- New Contact Tests ----------*/

   @Test
   public void newContactResourceIsValid() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());

      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newContactIsValid() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());

      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void newResourceDuplicateContact() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());

      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.uniqueContact(resource.getMethod(), resource.getValue(), resource
            .getPersonId())).thenReturn(1);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newDuplicateContact() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());

      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.uniqueContact(form.getMethod(), form.getValue(), form.getPersonId()))
         .thenReturn(1);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newResourceDuplicatePrimaryContact() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());

      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.uniqueContact(resource.getMethod(), resource.getValue(), resource
            .getPersonId())).thenReturn(0);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newContactResourceInvalidValue() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());
      resource.setValue("555-123");

      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void newContactInvalidValue() {
      final PersonEntry person = TestUtils.getPerson();
      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());
      form.setValue("555-123");

      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   /*---------- Existing Contact Tests ----------*/

   @Test
   public void updateContactResourceIsValid() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      person.getContacts().get(0).setPrimary(true);

      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());
      resource.setId(person.primaryContact().getId());
      resource.setValue("888-555-1234");

      Mockito.when(personQueryRepository.checkContactExists(resource.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateContact(resource.getMethod(), resource.getValue(), resource
            .getPersonId(), resource.getId())).thenReturn(0);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void updateContactIsValid() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      person.getContacts().get(0).setPrimary(true);

      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());
      form.setId(person.primaryContact().getId());
      form.setValue("888-555-1234");

      Mockito.when(personQueryRepository.checkContactExists(form.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateContact(form.getMethod(), form.getValue(), form.getPersonId(),
            form.getId())).thenReturn(0);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertFalse(errors.hasErrors());
   }

   @Test
   public void updateContactResourceToDuplicate() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final String contactId = IdentifierFactory.getInstance().generateIdentifier();
      final ContactEntry secondContact = TestUtils.getTelephoneContact();
      secondContact.setId(contactId);
      secondContact.setValue("212-555-1212");
      person.addContact(secondContact);

      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());
      resource.setPrimary(false);
      resource.setId(contactId);

      Mockito.when(personQueryRepository.checkContactExists(resource.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateContact(resource.getMethod(), resource.getValue(), resource
            .getPersonId(), resource.getId())).thenReturn(1);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateContactToDuplicate() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();

      final String contactId = IdentifierFactory.getInstance().generateIdentifier();
      final ContactEntry secondContact = TestUtils.getTelephoneContact();
      secondContact.setId(contactId);
      secondContact.setValue("212-555-1212");
      person.addContact(secondContact);

      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());
      form.setPrimary(false);
      form.setId(contactId);

      Mockito.when(personQueryRepository.checkContactExists(form.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateContact(form.getMethod(), form.getValue(), form.getPersonId(),
            form.getId())).thenReturn(1);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateContactResourceMultiplePrimary() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      person.getContacts().get(0).setPrimary(true);

      final String contactId = IdentifierFactory.getInstance().generateIdentifier();
      final ContactEntry secondContact = TestUtils.getTelephoneContact();
      secondContact.setId(contactId);
      secondContact.setValue("212-555-1212");
      secondContact.setPrimary(false);
      person.addContact(secondContact);

      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());
      secondContact.setValue("212-555-1212");
      resource.setPrimary(true);
      resource.setId(contactId);

      Mockito.when(personQueryRepository.checkContactExists(resource.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateContact(resource.getMethod(), resource.getValue(), resource
            .getPersonId(), resource.getId())).thenReturn(0);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateContactMultiplePrimary() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      person.getContacts().get(0).setPrimary(true);

      final String contactId = IdentifierFactory.getInstance().generateIdentifier();
      final ContactEntry secondContact = TestUtils.getTelephoneContact();
      secondContact.setId(contactId);
      secondContact.setValue("212-555-1212");
      secondContact.setPrimary(false);
      person.addContact(secondContact);

      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());
      secondContact.setValue("212-555-1212");
      form.setPrimary(true);
      form.setId(contactId);

      Mockito.when(personQueryRepository.checkContactExists(form.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateContact(form.getMethod(), form.getValue(), form.getPersonId(),
            form.getId())).thenReturn(0);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateContactResourceInvalidValue() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      person.getContacts().get(0).setPrimary(true);

      final String contactId = IdentifierFactory.getInstance().generateIdentifier();
      final ContactEntry secondContact = TestUtils.getTelephoneContact();
      secondContact.setId(contactId);
      secondContact.setValue("212-555-1212");
      secondContact.setPrimary(false);
      person.addContact(secondContact);

      final ContactResourceImpl resource = TestUtils.newContactResource();
      resource.setPersonId(person.getId());
      secondContact.setValue("212-555");
      resource.setId(contactId);

      Mockito.when(personQueryRepository.checkContactExists(resource.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(resource.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateContact(resource.getMethod(), resource.getValue(), resource
            .getPersonId(), resource.getId())).thenReturn(0);

      final BindException errors = new BindException(resource, "contactResource");
      ValidationUtils.invokeValidator(validator, resource, errors);
      assertTrue(errors.hasErrors());
   }

   @Test
   public void updateContactInvalidValue() {
      final PersonEntry person = TestUtils.getPersonWithPrimaryContact();
      person.getContacts().get(0).setPrimary(true);

      final String contactId = IdentifierFactory.getInstance().generateIdentifier();
      final ContactEntry secondContact = TestUtils.getTelephoneContact();
      secondContact.setId(contactId);
      secondContact.setValue("212-555-1212");
      secondContact.setPrimary(false);
      person.addContact(secondContact);

      final ContactForm form = TestUtils.newContactForm();
      form.setPersonId(person.getId());
      secondContact.setValue("212-555");
      form.setId(contactId);

      Mockito.when(personQueryRepository.checkContactExists(form.getId())).thenReturn(true);
      Mockito.when(personQueryRepository.findOne(form.getPersonId())).thenReturn(person);
      Mockito.when(
         personQueryRepository.updateContact(form.getMethod(), form.getValue(), form.getPersonId(),
            form.getId())).thenReturn(0);

      final BindException errors = new BindException(form, "contactForm");
      ValidationUtils.invokeValidator(validator, form, errors);
      assertTrue(errors.hasErrors());
   }
}
