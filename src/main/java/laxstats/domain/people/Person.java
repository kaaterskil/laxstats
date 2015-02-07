package laxstats.domain.people;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import laxstats.api.people.AddressAddedEvent;
import laxstats.api.people.AddressChangedEvent;
import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressDeletedEvent;
import laxstats.api.people.ContactAddedEvent;
import laxstats.api.people.ContactChangedEvent;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.ContactDeletedEvent;
import laxstats.api.people.Contactable;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.people.HasPrimaryContactException;
import laxstats.api.people.PersonCreatedEvent;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonDeletedEvent;
import laxstats.api.people.PersonId;
import laxstats.api.people.PersonUpdatedEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDate;

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

	/*---------- Constructors ----------*/

	public Person(PersonId personId, PersonDTO personDTO) {
		apply(new PersonCreatedEvent(personId, personDTO));
	}

	protected Person() {
	}

	/* ---------- Methods ---------- */

	public void update(PersonId personId, PersonDTO personDTO) {
		apply(new PersonUpdatedEvent(personId, personDTO));
	}

	public void delete(PersonId personId) {
		apply(new PersonDeletedEvent(personId));
	}

	public void registerAddress(AddressDTO dto, boolean overridePrimary) {
		if (dto.isPrimary() && hasPrimaryAddress() && !overridePrimary) {
			final String value = primaryAddress().getAddress();
			throw new HasPrimaryContactException(value);
		}
		if (addressExists(dto.getId())) {
			apply(new AddressChangedEvent(id, dto));
		} else {
			apply(new AddressAddedEvent(id, dto));
		}
	}

	public void updateAddress(AddressDTO dto) {
		apply(new AddressChangedEvent(id, dto));
	}

	public void deleteAddress(String addressId) {
		apply(new AddressDeletedEvent(id, addressId));
	}

	private boolean hasPrimaryAddress() {
		return primaryAddress() != null;
	}

	public Address primaryAddress() {
		final List<Contactable> list = new ArrayList<Contactable>(addresses);
		return (Address) getPrimaryContactOrAddress(list);
	}

	private boolean addressExists(String id) {
		for (final Address a : addresses) {
			if (a.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public void registerContact(ContactDTO dto, boolean overridePrimary) {
		if (dto.isPrimary() && hasPrimaryContact() && !overridePrimary) {
			throw new HasPrimaryContactException(primaryContact().getValue());
		}
		if (contactExists(dto.getId())) {
			apply(new ContactChangedEvent(id, dto));
		} else {
			apply(new ContactAddedEvent(id, dto));
		}
	}

	public void updateContact(ContactDTO dto) {
		apply(new ContactChangedEvent(id, dto));
	}

	public void deleteContact(String contactId) {
		apply(new ContactDeletedEvent(id, contactId));
	}

	private boolean hasPrimaryContact() {
		return primaryContact() != null;
	}

	public Contact primaryContact() {
		final List<Contactable> list = new ArrayList<Contactable>(contacts);
		return (Contact) getPrimaryContactOrAddress(list);
	}

	private boolean contactExists(String id) {
		for (final Contact c : contacts) {
			if (c.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

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

	/* ---------- Event handlers ---------- */

	@EventSourcingHandler
	protected void handle(PersonCreatedEvent event) {
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

	@EventSourcingHandler
	protected void handle(PersonUpdatedEvent event) {
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

	@EventSourcingHandler
	protected void handle(PersonDeletedEvent event) {
		markDeleted();
	}

	@EventSourcingHandler
	protected void handle(AddressAddedEvent event) {
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

	@EventSourcingHandler
	protected boolean handle(AddressDeletedEvent event) {
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

	@EventSourcingHandler
	protected void handle(ContactAddedEvent event) {
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

	@EventSourcingHandler
	protected boolean handle(ContactDeletedEvent event) {
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

	/* ---------- Getters ---------- */

	@Override
	public PersonId getIdentifier() {
		return id;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getNickname() {
		return nickname;
	}

	public String getFullName() {
		return fullName;
	}

	public Gender getGender() {
		return gender;
	}

	public DominantHand getDominantHand() {
		return dominantHand;
	}

	public boolean isReleased() {
		return released;
	}

	public LocalDate getParentReleaseSentOn() {
		return parentReleaseSentOn;
	}

	public LocalDate getParentReleaseReceivedOn() {
		return parentReleaseReceivedOn;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public String getCollege() {
		return college;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public Set<RelationshipInfo> getChildRelationships() {
		return childRelationships;
	}

	public Set<RelationshipInfo> getParentRelationships() {
		return parentRelationships;
	}
}
