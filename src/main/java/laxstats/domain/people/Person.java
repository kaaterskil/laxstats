package laxstats.domain.people;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import laxstats.api.people.AddressAddedEvent;
import laxstats.api.people.AddressChangedEvent;
import laxstats.api.people.AddressDTO;
import laxstats.api.people.ContactAddedEvent;
import laxstats.api.people.ContactChangedEvent;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.people.PersonCreatedEvent;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonId;
import laxstats.domain.players.Player;
import laxstats.query.events.EventAttendee;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
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
	private final boolean isParentReleased = false;
	private LocalDate parentReleaseSentOn;
	private LocalDate parentReleaseReceivedOn;
	private LocalDate birthdate;
	private String photo;
	private String college;
	private String collegeUrl;
	private final Map<String, Address> addresses = new HashMap<>();
	private final Map<String, Contact> contacts = new HashMap<>();
	private final Set<Relationship> childRelationships = new HashSet<>();
	private final Set<Relationship> parentRelationships = new HashSet<>();
	private final Set<EventAttendee> attendedEvents = new HashSet<>();
	private final Set<Player> playedSeasons = new HashSet<>();

	public Person(PersonId personId, PersonDTO personDTO) {
		apply(new PersonCreatedEvent(personId, personDTO));
	}

	protected Person() {
	}

	// ---------- Methods ----------//

	public void registerAddress(AddressDTO addressDTO) {
		if (addresses.containsKey(addressDTO.getId())) {
			apply(new AddressChangedEvent(id, addressDTO));
		} else {
			apply(new AddressAddedEvent(id, addressDTO));
		}
	}

	public void updateAddress(AddressDTO addressDTO) {
		apply(new AddressChangedEvent(id, addressDTO));
	}

	public void registerContact(ContactDTO contactDTO) {
		if (contacts.containsKey(contactDTO.getId())) {
			apply(new ContactChangedEvent(id, contactDTO));
		} else {
			apply(new ContactAddedEvent(id, contactDTO));
		}
	}

	public void updateContact(ContactDTO contactDTO) {
		apply(new ContactChangedEvent(id, contactDTO));
	}

	// ---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(PersonCreatedEvent event) {
		final PersonDTO dto = event.getPersonDTO();

		id = event.getPersonId();
		prefix = dto.getPrefix();
		firstName = dto.getFirstName();
		middleName = dto.getMiddleName();
		lastName = dto.getLastName();
		suffix = dto.getSuffix();
		nickname = dto.getNickname();
		fullName = dto.fullName();
		gender = dto.getGender();
		dominantHand = dto.getDominantHand();
		birthdate = dto.getBirthdate();
	}

	@EventSourcingHandler
	protected void handle(AddressAddedEvent event) {
		final String addressId = event.getAddress().getId();
		final AddressDTO dto = event.getAddress();

		final Address address = new Address();
		address.setPersonId(dto.getPerson().getId());
		address.setAddress1(dto.getAddress1());
		address.setAddress2(dto.getAddress2());
		address.setCity(dto.getCity());
		address.setRegion(dto.getRegion());
		address.setPostalCode(dto.getPostalCode());
		address.setAddressType(dto.getAddressType());
		address.setPrimary(dto.isPrimary());
		address.setDoNotUse(dto.isDoNotUse());

		addresses.put(addressId, address);
	}

	@EventSourcingHandler
	protected void handle(AddressChangedEvent event) {
		final String addressId = event.getAddressDTO().getId();
		final AddressDTO dto = event.getAddressDTO();

		final Address address = addresses.get(addressId);
		address.setAddressType(dto.getAddressType());
		address.setAddress1(dto.getAddress1());
		address.setAddress2(dto.getAddress2());
		address.setCity(dto.getCity());
		address.setRegion(dto.getRegion());
		address.setPostalCode(dto.getPostalCode());
		address.setPrimary(dto.isPrimary());
		address.setDoNotUse(dto.isDoNotUse());
	}

	@EventSourcingHandler
	protected void handle(ContactAddedEvent event) {
		final String contactId = event.getContact().getId();
		final ContactDTO dto = event.getContact();

		final Contact contact = new Contact();
		contact.setId(dto.getId());
		contact.setPersonId(id.toString());
		contact.setMethod(dto.getMethod());
		contact.setValue(dto.getValue());
		contact.setPrimary(dto.isPrimary());
		contact.setDoNotUse(dto.isDoNotUse());

		contacts.put(contactId, contact);
	}

	@EventSourcingHandler
	protected void handle(ContactChangedEvent event) {
		final String contactId = event.getContact().getId();
		final ContactDTO dto = event.getContact();

		final Contact contact = contacts.get(contactId);
		contact.setMethod(dto.getMethod());
		contact.setValue(dto.getValue());
		contact.setPrimary(dto.isPrimary());
		contact.setDoNotUse(dto.isDoNotUse());
	}

	// ---------- Getters ----------//

	public PersonId getId() {
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

	public boolean isParentReleased() {
		return isParentReleased;
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

	public String getPhoto() {
		return photo;
	}

	public String getCollege() {
		return college;
	}

	public String getCollegeUrl() {
		return collegeUrl;
	}

	public Map<String, Address> getAddresses() {
		return addresses;
	}

	public Map<String, Contact> getContacts() {
		return contacts;
	}

	public Set<Relationship> getChildRelationships() {
		return childRelationships;
	}

	public Set<Relationship> getParentRelationships() {
		return parentRelationships;
	}

	public Set<EventAttendee> getAttendedEvents() {
		return attendedEvents;
	}

	public Set<Player> getPlayedSeasons() {
		return playedSeasons;
	}
}
