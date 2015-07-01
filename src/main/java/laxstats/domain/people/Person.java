package laxstats.domain.people;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import laxstats.api.people.AddressAdded;
import laxstats.api.people.AddressChanged;
import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressDeleted;
import laxstats.api.people.ContactAdded;
import laxstats.api.people.ContactChanged;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.ContactDeleted;
import laxstats.api.people.Contactable;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.people.HasPrimaryContactException;
import laxstats.api.people.PersonCreated;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonDeleted;
import laxstats.api.people.PersonId;
import laxstats.api.people.PersonUpdated;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDate;

/**
 * {@code Person} is a domain object model of a person aggregate.
 */
public class Person extends AbstractAnnotatedAggregateRoot<PersonId> {
   private static final long serialVersionUID = -1073698329248234019L;

   @AggregateIdentifier
   private PersonId id;

   private String prefix;
   private String firstName;
   private String middleName;
   private String lastName;
   private String suffix;
   private String nickname;
   private String fullName;
   private Gender gender;
   private DominantHand dominantHand;
   private boolean released = false;
   private LocalDate parentReleaseSentOn;
   private LocalDate parentReleaseReceivedOn;
   private LocalDate birthdate;
   private String college;
   private final Set<RelationshipInfo> childRelationships = new HashSet<>();
   private final Set<RelationshipInfo> parentRelationships = new HashSet<>();

   @EventSourcedMember
   private final List<Address> addresses = new ArrayList<>();

   @EventSourcedMember
   private final List<Contact> contacts = new ArrayList<>();

   /**
    * Applies the given aggregate identifier and information to a newly created person.
    * 
    * @param personId
    * @param personDTO
    */
   public Person(PersonId personId, PersonDTO personDTO) {
      apply(new PersonCreated(personId, personDTO));
   }

   /**
    * Creates a new {@code Person}. Internal use only.
    */
   protected Person() {
   }

   /**
    * Instructs the framework to update and persits changes to this person.
    * 
    * @param personId
    * @param personDTO
    */
   public void update(PersonId personId, PersonDTO personDTO) {
      apply(new PersonUpdated(personId, personDTO));
   }

   /**
    * Instructs the framework to mark this person for deletion.
    * 
    * @param personId
    */
   public void delete(PersonId personId) {
      apply(new PersonDeleted(personId));
   }

   /**
    * Instructs the framework to create a new address with information contained in the given event,
    * and add it to the person's list of addresses. If the new address is the primary address, any
    * existing primary address will be downgraded if and only if the given override flag is set to
    * true. Otherwise, a HasPrimaryContactException is thrown. If the address already exists in the
    * person's list of addresses, it is updated.
    *
    * @param dto
    * @param overridePrimary
    */
   public void registerAddress(AddressDTO dto, boolean overridePrimary) {
      if (dto.isPrimary() && hasPrimaryAddress() && !overridePrimary) {
         final String value = primaryAddress().getAddress();
         throw new HasPrimaryContactException(value);
      }
      if (addressExists(dto.getId())) {
         apply(new AddressChanged(id, dto));
      }
      else {
         apply(new AddressAdded(id, dto));
      }
   }

   /**
    * Instructs the framework to update and persist an address with the given information.
    *
    * @param dto
    */
   public void updateAddress(AddressDTO dto) {
      apply(new AddressChanged(id, dto));
   }

   /**
    * Instructs the framework to delete the address matching the given identifier.
    *
    * @param addressId
    */
   public void deleteAddress(String addressId) {
      apply(new AddressDeleted(id, addressId));
   }

   /**
    * Returns true if the person has a primary address, false otherwise.
    *
    * @return
    */
   private boolean hasPrimaryAddress() {
      return primaryAddress() != null;
   }

   /**
    * Returns the person's primary address. If no primary address is found, the first address in the
    * person's list of addresses is returned.
    *
    * @return
    */
   public Address primaryAddress() {
      final List<Contactable> list = new ArrayList<Contactable>(addresses);
      return (Address)getPrimaryContactOrAddress(list);
   }

   /**
    * Returns true if the address matching the given identifier exists in the persosn's list of
    * addresses, false otherwise.
    *
    * @param id
    * @return
    */
   private boolean addressExists(String id) {
      for (final Address a : addresses) {
         if (a.getId().equals(id)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Instructs the framework to create a new contact with information contained in the given event,
    * and add it to the person's list of contacts. If the new contact is the primary contact, any
    * existing primary contact will be downgraded if and only if the given override flag is set to
    * true. Otherwise, a HasPrimaryContactException is thrown. If the contact already exists in the
    * person's list of contacts, it is updated.
    *
    * @param dto
    * @param overridePrimary
    */
   public void registerContact(ContactDTO dto, boolean overridePrimary) {
      if (dto.isPrimary() && hasPrimaryContact() && !overridePrimary) {
         throw new HasPrimaryContactException(primaryContact().getValue());
      }
      if (contactExists(dto.getId())) {
         apply(new ContactChanged(id, dto));
      }
      else {
         apply(new ContactAdded(id, dto));
      }
   }

   /**
    * Instructs the framework to update and persist changes to a contact with the given information.
    *
    * @param dto
    */
   public void updateContact(ContactDTO dto) {
      apply(new ContactChanged(id, dto));
   }

   /**
    * Instructs the framework to delete the contact matching the given identifier.
    *
    * @param contactId
    */
   public void deleteContact(String contactId) {
      apply(new ContactDeleted(id, contactId));
   }

   /**
    * Returns true if the person has a primary contact, false otherwise.
    *
    * @return
    */
   private boolean hasPrimaryContact() {
      return primaryContact() != null;
   }

   /**
    * Returns the primary contact. If no contact is designated as primary, the method returns the
    * first contact in the person's list.
    *
    * @return
    */
   public Contact primaryContact() {
      final List<Contactable> list = new ArrayList<Contactable>(contacts);
      return (Contact)getPrimaryContactOrAddress(list);
   }

   /**
    * Returns true if an address of contact matching the given identifier exists in this person's
    * list of address or contacts, false otherwise.
    *
    * @param id
    * @return
    */
   private boolean contactExists(String id) {
      for (final Contact c : contacts) {
         if (c.getId().equals(id)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns the primary address or contact from the given list of addresses or contacts. If no
    * primary address or contact is found, the first address or contact in the list is returned.
    *
    * @param c
    * @return
    */
   private Contactable getPrimaryContactOrAddress(List<Contactable> c) {
      Contactable primary = null;
      Contactable first = null;
      int i = 0;
      for (final Contactable each : c) {
         if (i == 0) {
            first = each;
         }
         if (each.isPrimary()) {
            primary = each;
         }
         i++;
      }
      return primary != null ? primary : first;
   }

   /**
    * Stores and persits information for a new contact from information contained in the given
    * event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PersonCreated event) {
      final PersonDTO dto = event.getPersonDTO();
      id = event.getPersonId();
      prefix = dto.getPrefix();
      firstName = dto.getFirstName();
      middleName = dto.getMiddleName();
      lastName = dto.getLastName();
      suffix = dto.getSuffix();
      fullName = dto.fullName();
      nickname = dto.getNickname();
      gender = dto.getGender();
      dominantHand = dto.getDominantHand();
      birthdate = dto.getBirthdate();
      released = dto.isParentReleased();
      parentReleaseSentOn = dto.getParentReleaseSentOn();
      parentReleaseReceivedOn = dto.getParentReleaseReceivedOn();
   }

   /**
    * Updates and persists changes to this person with information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PersonUpdated event) {
      final PersonDTO dto = event.getPersonDTO();
      prefix = dto.getPrefix();
      firstName = dto.getFirstName();
      middleName = dto.getMiddleName();
      lastName = dto.getLastName();
      suffix = dto.getSuffix();
      fullName = dto.getFullName();
      nickname = dto.getNickname();
      gender = dto.getGender();
      dominantHand = dto.getDominantHand();
      birthdate = dto.getBirthdate();
      released = dto.isParentReleased();
      parentReleaseSentOn = dto.getParentReleaseSentOn();
      parentReleaseReceivedOn = dto.getParentReleaseReceivedOn();
   }

   /**
    * Marks this person for deletion.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PersonDeleted event) {
      markDeleted();
   }

   /**
    * Creates and persists a new address from information in the given event, and adds it to the
    * persons's set of addresses.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(AddressAdded event) {
      final AddressDTO dto = event.getAddress();

      final Address address = new Address();
      address.setPersonId(id.toString());
      address.setAddress1(dto.getAddress1());
      address.setAddress2(dto.getAddress2());
      address.setCity(dto.getCity());
      address.setRegion(dto.getRegion());
      address.setPostalCode(dto.getPostalCode());
      address.setAddressType(dto.getAddressType());
      address.setPrimary(dto.isPrimary());
      address.setDoNotUse(dto.isDoNotUse());
      addresses.add(address);
   }

   /**
    * Removes and deletes the address matching the identifier in the given event from the person's
    * list of addresses.
    *
    * @param event
    * @return
    */
   @EventSourcingHandler
   protected boolean handle(AddressDeleted event) {
      final String addressId = event.getAddressId();

      final Iterator<Address> iter = addresses.iterator();
      while (iter.hasNext()) {
         final Address each = iter.next();
         if (each.getId().equals(addressId)) {
            iter.remove();
            return true;
         }
      }
      return false;
   }

   /**
    * Creates and persists a new contact from information in the given event and adds it to this
    * person's set of contacts.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(ContactAdded event) {
      final ContactDTO dto = event.getContact();

      final Contact contact = new Contact();
      contact.setId(dto.getId());
      contact.setPersonId(id.toString());
      contact.setMethod(dto.getMethod());
      contact.setValue(dto.getValue());
      contact.setPrimary(dto.isPrimary());
      contact.setDoNotUse(dto.isDoNotUse());
      contacts.add(contact);
   }

   /**
    * Removes and deletes the contact matching the identifier in the given event from the person's
    * set of contacts.
    *
    * @param event
    * @return
    */
   @EventSourcingHandler
   protected boolean handle(ContactDeleted event) {
      final String contactId = event.getContactId();

      final Iterator<Contact> iter = contacts.iterator();
      while (iter.hasNext()) {
         final Contact each = iter.next();
         if (each.getId().equals(contactId)) {
            iter.remove();
            return true;
         }
      }
      return false;
   }

   /**
    * Returns the aggregate identifier of the person. {@inheritDoc}
    */
   @Override
   public PersonId getIdentifier() {
      return id;
   }

   /**
    * returns the persosn's name prefix.
    *
    * @return
    */
   public String getPrefix() {
      return prefix;
   }

   /**
    * Returnst the person's first name.
    *
    * @return
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * Returns the person's middle name.
    *
    * @return
    */
   public String getMiddleName() {
      return middleName;
   }

   /**
    * Returns the person's last name. Never null.
    *
    * @return
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Returns the person's name suffix.
    *
    * @return
    */
   public String getSuffix() {
      return suffix;
   }

   /**
    * Returns the person's nickname.
    *
    * @return
    */
   public String getNickname() {
      return nickname;
   }

   /**
    * Returns the person's full name.
    *
    * @return
    */
   public String getFullName() {
      return fullName;
   }

   /**
    * Returns the person's gender.
    *
    * @return
    */
   public Gender getGender() {
      return gender;
   }

   /**
    * Returns the person's dominant hand.
    *
    * @return
    */
   public DominantHand getDominantHand() {
      return dominantHand;
   }

   /**
    * Returns if the person has a parental release, false otherwise.
    *
    * @return
    */
   public boolean isReleased() {
      return released;
   }

   /**
    * Returns the date the person's parental release was sent.
    *
    * @return
    */
   public LocalDate getParentReleaseSentOn() {
      return parentReleaseSentOn;
   }

   /**
    * Returns the date the person's parental release was received.
    *
    * @return
    */
   public LocalDate getParentReleaseReceivedOn() {
      return parentReleaseReceivedOn;
   }

   /**
    * Returns the person's birth date.
    *
    * @return
    */
   public LocalDate getBirthdate() {
      return birthdate;
   }

   /**
    * Returns the name of the person's college.
    *
    * @return
    */
   public String getCollege() {
      return college;
   }

   /**
    * Returns the list of addresses.
    *
    * @return
    */
   public List<Address> getAddresses() {
      return addresses;
   }

   /**
    * Returns the list of contacts.
    *
    * @return
    */
   public List<Contact> getContacts() {
      return contacts;
   }

   /**
    * Returns the set of child relationships.
    *
    * @return
    */
   public Set<RelationshipInfo> getChildRelationships() {
      return childRelationships;
   }

   /**
    * Returns the set of parent relationships.
    *
    * @return
    */
   public Set<RelationshipInfo> getParentRelationships() {
      return parentRelationships;
   }
}
