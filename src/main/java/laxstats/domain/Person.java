package laxstats.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
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

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "people_idx1", columnList = "lastName"),
		@Index(name = "people_idx2", columnList = "isParentReleased"),
		@Index(name = "people_idx3", columnList = "parentReleaseSentOn"),
		@Index(name = "people_idx4", columnList = "parentReleaseReceivedOn")
	}
)
public class Person {
	
	public enum DominantHand {
		RIGHT, LEFT, AMBIDEXTROUS;
	}

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
	private Person.DominantHand dominantHand;	
	
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
	
	@ManyToOne(targetEntity = User.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne(targetEntity = User.class)
	private String modifiedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<Address> addresses = new HashSet<Address>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<Contact> contacts = new HashSet<Contact>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private Set<Relationship> childRelationships = new HashSet<Relationship>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "child")
	private Set<Relationship> parentRelationships = new HashSet<Relationship>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<EventAttendee> attendedEvents = new HashSet<EventAttendee>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<TeamMember> playedSeasons = new HashSet<TeamMember>();
	
	//---------- Getter/Setters ----------//
	
	public String getId() {
		return id;
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

	public Person.DominantHand getDominantHand() {
		return dominantHand;
	}

	public void setDominantHand(Person.DominantHand dominantHand) {
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getCollegeUrl() {
		return collegeUrl;
	}

	public void setCollegeUrl(String collegeUrl) {
		this.collegeUrl = collegeUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public Set<Address> getAddresses() {
		return addresses;
	}

	public boolean addAddress(Address address) {
		address.setPerson(this);
		return addresses.add(address);
	}

	public Set<EventAttendee> getAttendedEvents() {
		return attendedEvents;
	}

	public Set<TeamMember> getPlayedSeasons() {
		return playedSeasons;
	}

	Set<Person> getParents(Relationship.Type type) {
		Set<Person> result = new HashSet<Person>();
		Iterator<Relationship> iter = parentRelationships.iterator();
		while(iter.hasNext()) {
			Relationship each = iter.next();
			if(each.getType().equals(type)) {
				result.add(each.getParent());
			}
		}
		return result;
	}
	
	Set<Person> getChildren(Relationship.Type type) {
		Set<Person> result = new HashSet<Person>();
		Iterator<Relationship> iter = childRelationships.iterator();
		while(iter.hasNext()) {
			Relationship each = iter.next();
			if(each.getType().equals(type)) {
				result.add(each.getChild());
			}
		}
		return result;
	}
	
	void addParentRelationship(Relationship relationship) {
		parentRelationships.add(relationship);
	}
	
	void addChildRelationship(Relationship relationship) {
		childRelationships.add(relationship);
	}
	
	boolean ancestorsInclude(Person sample, Relationship.Type type) {
		Iterator<Person> iter = getParents(type).iterator();
		while(iter.hasNext()) {
			Person each = iter.next();
			if(each.equals(sample)) {
				return true;
			}
			if(each.ancestorsInclude(sample, type)) {
				return true;
			}
		}
		return false;
	}
}
