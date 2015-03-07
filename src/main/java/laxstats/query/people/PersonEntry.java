package laxstats.query.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import laxstats.api.people.Contactable;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.relationships.RelationshipType;
import laxstats.query.players.PlayerEntry;
import laxstats.query.relationships.RelationshipEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "people", indexes = {
		@Index(name = "people_idx1", columnList = "lastName"),
		@Index(name = "people_idx2", columnList = "isParentReleased"),
		@Index(name = "people_idx3", columnList = "parentReleaseSentOn"),
		@Index(name = "people_idx4", columnList = "parentReleaseReceivedOn"),
		@Index(name = "people_idx5", columnList = "lastName, firstName") })
public class PersonEntry implements Serializable {
	private static final long serialVersionUID = 7491763841222491421L;

	@Id
	@Column(length = 36)
	private String id;

	@Column(length = 10)
	private String prefix;

	@Column(length = 20)
	private String firstName;

	@Column(length = 20)
	private String middleName;

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

	@Column(length = 100)
	private String college;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "person")
	private final List<AddressEntry> addresses = new ArrayList<>();

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "person")
	private final List<ContactEntry> contacts = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private final Set<RelationshipEntry> childRelationships = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "child")
	private final Set<RelationshipEntry> parentRelationships = new HashSet<>();

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "person")
	private final Set<PlayerEntry> playedSeasons = new HashSet<>();

	/*---------- Methods ----------*/

	public boolean ancestorsInclude(PersonEntry sample, RelationshipType type) {
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

	public Set<PersonEntry> getChildren(RelationshipType type) {
		final Set<PersonEntry> result = new HashSet<>();
		for (final RelationshipEntry each : childRelationships) {
			if (type == null || each.getType().equals(type)) {
				result.add(each.getChild());
			}
		}
		return result;
	}

	public Set<PersonEntry> getParents(RelationshipType type) {
		final Set<PersonEntry> result = new HashSet<>();
		for (final RelationshipEntry each : parentRelationships) {
			if (type == null || each.getType().equals(type)) {
				result.add(each.getParent());
			}
		}
		return result;
	}

	public void addChildRelationship(RelationshipEntry relationship) {
		relationship.setParent(this);
		childRelationships.add(relationship);
	}

	public void addParentRelationship(RelationshipEntry relationship) {
		relationship.setChild(this);
		parentRelationships.add(relationship);
	}

	public AddressEntry getAddress(String id) {
		for (final AddressEntry each : addresses) {
			if (each.getId().equals(id)) {
				return each;
			}
		}
		return null;
	}

	public ContactEntry getContact(String id) {
		for (final ContactEntry each : contacts) {
			if (each.getId().equals(id)) {
				return each;
			}
		}
		return null;
	}

	/**
	 * Returns the person's primary address. If there is no primary address, the
	 * method returns the first address in the collection or <code>null</code>
	 * if the collection is empty.
	 *
	 * @return The person's primary address.
	 */
	public AddressEntry primaryAddress() {
		final List<Contactable> list = new ArrayList<>(addresses);
		return (AddressEntry) getPrimaryContactOrAddress(list);
	}

	/**
	 * Returns the person's primary contact. If there is no primary contact, the
	 * method returns the first contact in the collection or <code>null</code>
	 * if the collection is empty.
	 *
	 * @return The person's primary contact.
	 */
	public ContactEntry primaryContact() {
		final List<Contactable> list = new ArrayList<>(contacts);
		return (ContactEntry) getPrimaryContactOrAddress(list);
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

	/*---------- Getter/Setters ----------*/

	public void addAddress(AddressEntry address) {
		address.setPerson(this);
		addresses.add(address);
	}

	public void removeAddress(AddressEntry address) {
		address.setPerson(null);
		addresses.remove(address);
	}

	public void addContact(ContactEntry contact) {
		contact.setPerson(this);
		contacts.add(contact);
	}

	public void removeContact(ContactEntry contact) {
		contact.setPerson(null);
		contacts.remove(contact);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public DominantHand getDominantHand() {
		return dominantHand;
	}

	public void setDominantHand(DominantHand dominantHand) {
		this.dominantHand = dominantHand;
	}

	public boolean isParentReleased() {
		return isParentReleased;
	}

	public void setParentReleased(boolean isParentReleased) {
		this.isParentReleased = isParentReleased;
	}

	public LocalDate getParentReleaseSentOn() {
		return parentReleaseSentOn;
	}

	public void setParentReleaseSentOn(LocalDate parentReleaseSentOn) {
		this.parentReleaseSentOn = parentReleaseSentOn;
	}

	public LocalDate getParentReleaseReceivedOn() {
		return parentReleaseReceivedOn;
	}

	public void setParentReleaseReceivedOn(LocalDate parentReleaseReceivedOn) {
		this.parentReleaseReceivedOn = parentReleaseReceivedOn;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntry createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserEntry modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public List<AddressEntry> getAddresses() {
		return addresses;
	}

	public List<ContactEntry> getContacts() {
		return contacts;
	}

	public Set<RelationshipEntry> getChildRelationships() {
		return childRelationships;
	}

	public Set<RelationshipEntry> getParentRelationships() {
		return parentRelationships;
	}

	public Set<PlayerEntry> getPlayedSeasons() {
		return playedSeasons;
	}
}
