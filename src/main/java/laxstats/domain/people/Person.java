package laxstats.domain.people;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import laxstats.api.people.Address;
import laxstats.api.people.AddressAddedEvent;
import laxstats.api.people.AddressChangedEvent;
import laxstats.api.people.Contact;
import laxstats.api.people.ContactAddedEvent;
import laxstats.api.people.ContactChangedEvent;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.people.PersonCreatedEvent;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonId;
import laxstats.domain.teams.TeamMember;
import laxstats.query.events.EventAttendee;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

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
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
	private final Map<String, Address> addresses = new HashMap<String, Address>();
	private final Map<String, Contact> contacts = new HashMap<String, Contact>();
	private final Set<Relationship> childRelationships = new HashSet<Relationship>();
	private final Set<Relationship> parentRelationships = new HashSet<Relationship>();
	private final Set<EventAttendee> attendedEvents = new HashSet<EventAttendee>();
	private final Set<TeamMember> playedSeasons = new HashSet<TeamMember>();

	Person() {
	}

	public Person(PersonId personId, PersonDTO personDTO) {
		apply(new PersonCreatedEvent(personId, personDTO));
	}

	@EventHandler
	protected void handle(AddressAddedEvent event) {
		addresses.put(event.getAddress().getId(), event.getAddress());
	}

	@EventHandler
	protected void handle(ContactAddedEvent event) {
		contacts.put(event.getContact().getId(), event.getContact());
	}

	@EventHandler
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
		createdBy = dto.getCreatedBy();
		createdAt = dto.getCreatedAt();
		modifiedBy = dto.getModifiedBy();
		modifiedAt = dto.getModifiedAt();
	}

	public void registerAddress(Address address) {
		if (addresses.containsKey(address.getId())) {
			apply(new AddressChangedEvent(id, address));
		} else {
			apply(new AddressAddedEvent(id, address));
		}
	}

	public void registerContact(Contact contact) {
		if (contacts.containsKey(contact.getId())) {
			apply(new ContactChangedEvent(id, contact));
		} else {
			apply(new ContactAddedEvent(id, contact));
		}
	}
}
