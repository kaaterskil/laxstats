package laxstats.domain.people;

import java.util.HashSet;
import java.util.Set;

import laxstats.api.people.PersonCreatedEvent;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonId;
import laxstats.domain.DominantHand;
import laxstats.domain.Gender;
import laxstats.domain.teams.TeamMember;
import laxstats.query.events.EventAttendee;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class Person extends AbstractAnnotatedAggregateRoot<PersonId> {
	
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
	private boolean isParentReleased = false;
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
	private Set<Address> addresses = new HashSet<Address>();
	private Set<Contact> contacts = new HashSet<Contact>();
	private Set<Relationship> childRelationships = new HashSet<Relationship>();
	private Set<Relationship> parentRelationships = new HashSet<Relationship>();
	private Set<EventAttendee> attendedEvents = new HashSet<EventAttendee>();
	private Set<TeamMember> playedSeasons = new HashSet<TeamMember>();
	
	protected Person() {}
	
	public Person(PersonId personId, PersonDTO personDTO) {
		apply(new PersonCreatedEvent(personId, personDTO));
	}
}
