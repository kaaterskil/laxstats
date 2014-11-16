package laxstats.domain.people;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import laxstats.domain.Gender;
import laxstats.domain.teams.TeamMember;
import laxstats.query.events.EventAttendee;

import org.joda.time.LocalDateTime;

public class Person {

	public enum DominantHand {
		RIGHT, LEFT, AMBIDEXTROUS;
	}

	private String id;
	private String prefix;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String nickname;
	private String fullName;
	private Gender gender;
	private Person.DominantHand dominantHand;
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
}
