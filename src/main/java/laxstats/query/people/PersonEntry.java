package laxstats.query.people;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.query.events.EventAttendee;
import laxstats.query.teams.TeamMember;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = { @Index(name = "people_idx1", columnList = "lastName"),
		@Index(name = "people_idx2", columnList = "isParentReleased"),
		@Index(name = "people_idx3", columnList = "parentReleaseSentOn"),
		@Index(name = "people_idx4", columnList = "parentReleaseReceivedOn") })
public class PersonEntry {

	@Id
	@Column(length = 36)
	private String id;

	@Column(length = 10)
	private String prefix;

	@Column(length = 20)
	private String firstName;

	@Column(length = 20)
	private String middleName;

	@NotNull
	@Column(length = 30, nullable = false)
	private String lastName;

	@Column(length = 10)
	private String suffix;

	@Column(length = 20)
	private String nickname;

	@Column(length = 100)
	private String fullName;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private DominantHand dominantHand;

	private boolean isParentReleased = false;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate parentReleaseSentOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate parentReleaseReceivedOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate birthdate;

	private String photo;

	@Column(length = 100)
	private String college;

	private String collegeUrl;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private final Map<String, AddressEntry> addresses = new HashMap<String, AddressEntry>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private final Map<String, ContactEntry> contacts = new HashMap<String, ContactEntry>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private final Set<Relationship> childRelationships = new HashSet<Relationship>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "child")
	private final Set<Relationship> parentRelationships = new HashSet<Relationship>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private final Set<EventAttendee> attendedEvents = new HashSet<EventAttendee>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private final Set<TeamMember> playedSeasons = new HashSet<TeamMember>();

	// ---------- Getter/Setters ----------//

	public void addAddress(AddressEntry address) {
		address.setPerson(this);
		addresses.put(address.getId(), address);
	}

	void addChildRelationship(Relationship relationship) {
		childRelationships.add(relationship);
	}

	public void addContact(ContactEntry contact) {
		contact.setPerson(this);
		contacts.put(contact.getId(), contact);
	}

	void addParentRelationship(Relationship relationship) {
		parentRelationships.add(relationship);
	}

	boolean ancestorsInclude(PersonEntry sample, Relationship.Type type) {
		final Iterator<PersonEntry> iter = getParents(type).iterator();
		while (iter.hasNext()) {
			final PersonEntry each = iter.next();
			if (each.equals(sample)) {
				return true;
			}
			if (each.ancestorsInclude(sample, type)) {
				return true;
			}
		}
		return false;
	}

	public Map<String, AddressEntry> getAddresses() {
		return addresses;
	}

	public Set<EventAttendee> getAttendedEvents() {
		return attendedEvents;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	Set<PersonEntry> getChildren(Relationship.Type type) {
		final Set<PersonEntry> result = new HashSet<PersonEntry>();
		final Iterator<Relationship> iter = childRelationships.iterator();
		while (iter.hasNext()) {
			final Relationship each = iter.next();
			if (each.getType().equals(type)) {
				result.add(each.getChild());
			}
		}
		return result;
	}

	public String getCollege() {
		return college;
	}

	public String getCollegeUrl() {
		return collegeUrl;
	}

	public Map<String, ContactEntry> getContacts() {
		return contacts;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public DominantHand getDominantHand() {
		return dominantHand;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFullName() {
		return fullName;
	}

	public Gender getGender() {
		return gender;
	}

	public String getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public String getNickname() {
		return nickname;
	}

	public LocalDate getParentReleaseReceivedOn() {
		return parentReleaseReceivedOn;
	}

	public LocalDate getParentReleaseSentOn() {
		return parentReleaseSentOn;
	}

	Set<PersonEntry> getParents(Relationship.Type type) {
		final Set<PersonEntry> result = new HashSet<PersonEntry>();
		final Iterator<Relationship> iter = parentRelationships.iterator();
		while (iter.hasNext()) {
			final Relationship each = iter.next();
			if (each.getType().equals(type)) {
				result.add(each.getParent());
			}
		}
		return result;
	}

	public String getPhoto() {
		return photo;
	}

	public Set<TeamMember> getPlayedSeasons() {
		return playedSeasons;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public boolean isParentReleased() {
		return isParentReleased;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public void setCollegeUrl(String collegeUrl) {
		this.collegeUrl = collegeUrl;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setDominantHand(DominantHand dominantHand) {
		this.dominantHand = dominantHand;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setParentReleased(boolean isParentReleased) {
		this.isParentReleased = isParentReleased;
	}

	public void setParentReleaseReceivedOn(LocalDate parentReleaseReceivedOn) {
		this.parentReleaseReceivedOn = parentReleaseReceivedOn;
	}

	public void setParentReleaseSentOn(LocalDate parentReleaseSentOn) {
		this.parentReleaseSentOn = parentReleaseSentOn;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	// ---------- Other methods ----------//

	/**
	 * Returns the person's primary address. If there is no primary address, the
	 * method returns the first address in the collection.
	 *
	 * @return The person's primary address.
	 */
	public AddressEntry primaryAddress() {
		AddressEntry result = null;
		int i = 0;
		final Iterator<AddressEntry> iter = addresses.values().iterator();
		while (iter.hasNext()) {
			final AddressEntry entry = iter.next();
			if (entry.isPrimary() || i == 0) {
				result = entry;
			}
			i++;
		}
		return result;
	}

	/**
	 * REturns the person's primary contact. If there is no primary contact, the
	 * method returns the firstcontact in the collection.
	 *
	 * @return The person's primary contact.
	 */
	public ContactEntry primaryContact() {
		ContactEntry result = null;
		int i = 0;
		final Iterator<ContactEntry> iter = contacts.values().iterator();
		while (iter.hasNext()) {
			final ContactEntry entry = iter.next();
			if (entry.isPrimary() || i == 0) {
				result = entry;
			}
			i++;
		}
		return result;
	}
}
