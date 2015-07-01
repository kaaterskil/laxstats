package laxstats.query.people;

import laxstats.api.people.AddressAdded;
import laxstats.api.people.AddressChanged;
import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressDeleted;
import laxstats.api.people.ContactAdded;
import laxstats.api.people.ContactChanged;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.ContactDeleted;
import laxstats.api.people.PersonCreated;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonDeleted;
import laxstats.api.people.PersonId;
import laxstats.api.people.PersonUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code PersonListener} manages events that write to the query database.
 */
@Component
public class PersonListener {
   private PersonQueryRepository personRepository;

   @Autowired
   public void setPersonRepository(PersonQueryRepository personRepository) {
      this.personRepository = personRepository;
   }

   /**
    * Creates and persists a new person from information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(PersonCreated event) {
      final PersonDTO dto = event.getPersonDTO();

      final PersonEntry aggregate = new PersonEntry();
      aggregate.setId(event.getPersonId().toString());
      aggregate.setPrefix(dto.getPrefix());
      aggregate.setFirstName(dto.getFirstName());
      aggregate.setMiddleName(dto.getMiddleName());
      aggregate.setLastName(dto.getLastName());
      aggregate.setSuffix(dto.getSuffix());
      aggregate.setFullName(dto.fullName());
      aggregate.setNickname(dto.getNickname());
      aggregate.setGender(dto.getGender());
      aggregate.setDominantHand(dto.getDominantHand());
      aggregate.setBirthdate(dto.getBirthdate());
      aggregate.setParentReleased(dto.isParentReleased());
      aggregate.setParentReleaseSentOn(dto.getParentReleaseSentOn());
      aggregate.setParentReleaseReceivedOn(dto.getParentReleaseReceivedOn());
      aggregate.setCollege(dto.getCollege());
      aggregate.setCreatedAt(dto.getCreatedAt());
      aggregate.setCreatedBy(dto.getCreatedBy());
      aggregate.setModifiedAt(dto.getModifiedAt());
      aggregate.setModifiedBy(dto.getModifiedBy());

      personRepository.save(aggregate);
   }

   /**
    * Updates and persists changes to a person with information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(PersonUpdated event) {
      final String id = event.getPersonId().toString();
      final PersonDTO dto = event.getPersonDTO();

      final PersonEntry aggregate = personRepository.findOne(id);
      aggregate.setPrefix(dto.getPrefix());
      aggregate.setFirstName(dto.getFirstName());
      aggregate.setMiddleName(dto.getMiddleName());
      aggregate.setLastName(dto.getLastName());
      aggregate.setSuffix(dto.getSuffix());
      aggregate.setFullName(dto.fullName());
      aggregate.setNickname(dto.getNickname());
      aggregate.setGender(dto.getGender());
      aggregate.setDominantHand(dto.getDominantHand());
      aggregate.setBirthdate(dto.getBirthdate());
      aggregate.setParentReleased(dto.isParentReleased());
      aggregate.setParentReleaseSentOn(dto.getParentReleaseSentOn());
      aggregate.setParentReleaseReceivedOn(dto.getParentReleaseReceivedOn());
      aggregate.setCollege(dto.getCollege());
      aggregate.setModifiedAt(dto.getModifiedAt());
      aggregate.setModifiedBy(dto.getModifiedBy());
   }

   /**
    * Deletes the person matching the identifier contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(PersonDeleted event) {
      final String id = event.getPersonId().toString();
      personRepository.delete(id);
   }

   /*---------- Addresses ----------*/

   /**
    * Creates and persists an address from information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(AddressAdded event) {
      final AddressDTO dto = event.getAddress();
      final PersonEntry aggregate = personRepository.findOne(event.getPersonId().toString());

      final AddressEntry address = new AddressEntry();
      address.setId(dto.getId());
      address.setAddressType(dto.getAddressType());
      address.setAddress1(dto.getAddress1());
      address.setAddress2(dto.getAddress2());
      address.setCity(dto.getCity());
      address.setRegion(dto.getRegion());
      address.setPostalCode(dto.getPostalCode());
      address.setPrimary(dto.isPrimary());
      address.setDoNotUse(dto.isDoNotUse());
      address.setCreatedAt(dto.getCreatedAt());
      address.setCreatedBy(dto.getCreatedBy());
      address.setModifiedAt(dto.getModifiedAt());
      address.setModifiedBy(dto.getModifiedBy());

      aggregate.addAddress(address);
      personRepository.save(aggregate);
   }

   /**
    * Updates and persists changes to an address with information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(AddressChanged event) {
      final PersonId identifier = event.getPersonId();
      final AddressDTO dto = event.getAddressDTO();
      final PersonEntry aggregate = personRepository.findOne(identifier.toString());

      final AddressEntry address = aggregate.getAddress(dto.getId());
      address.setAddressType(dto.getAddressType());
      address.setAddress1(dto.getAddress1());
      address.setAddress2(dto.getAddress2());
      address.setCity(dto.getCity());
      address.setRegion(dto.getRegion());
      address.setPostalCode(dto.getPostalCode());
      address.setPrimary(dto.isPrimary());
      address.setDoNotUse(dto.isDoNotUse());
      address.setModifiedAt(dto.getModifiedAt());
      address.setModifiedBy(dto.getModifiedBy());

      personRepository.save(aggregate);
   }

   /**
    * Deletes the address matching the identifier contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(AddressDeleted event) {
      final String personId = event.getPersonId().toString();
      final PersonEntry entity = personRepository.findOne(personId);
      final AddressEntry address = entity.getAddress(event.getAddressId());

      entity.removeAddress(address);
      personRepository.save(entity);
   }

   /*---------- Contacts ----------*/

   /**
    * Creates and persists a contact from information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ContactAdded event) {
      final String personId = event.getPersonId().toString();
      final ContactDTO dto = event.getContact();
      final PersonEntry entity = personRepository.findOne(personId);

      final ContactEntry contact = new ContactEntry();
      contact.setId(dto.getId());
      contact.setMethod(dto.getMethod());
      contact.setValue(dto.getValue());
      contact.setPrimary(dto.isPrimary());
      contact.setDoNotUse(dto.isDoNotUse());
      contact.setCreatedAt(dto.getCreatedAt());
      contact.setCreatedBy(dto.getCreatedBy());
      contact.setModifiedAt(dto.getModifiedAt());
      contact.setModifiedBy(dto.getModifiedBy());

      entity.addContact(contact);
      personRepository.save(entity);
   }

   /**
    * Updates and persists changes to a contact with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ContactChanged event) {
      final String personId = event.getPersonId().toString();
      final ContactDTO dto = event.getContact();
      final PersonEntry entity = personRepository.findOne(personId);

      final ContactEntry contact = entity.getContact(dto.getId());
      contact.setMethod(dto.getMethod());
      contact.setValue(dto.getValue());
      contact.setPrimary(dto.isPrimary());
      contact.setDoNotUse(dto.isDoNotUse());
      contact.setModifiedAt(dto.getModifiedAt());
      contact.setModifiedBy(dto.getModifiedBy());

      personRepository.save(entity);
   }

   /**
    * Deletes the contact matching the identifier contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ContactDeleted event) {
      final String personId = event.getPersonId().toString();
      final PersonEntry entity = personRepository.findOne(personId);
      final ContactEntry contact = entity.getContact(event.getContactId());

      entity.removeContact(contact);
      personRepository.save(entity);
   }
}
