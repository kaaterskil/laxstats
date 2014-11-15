package laxstats.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
public class Person {
	
	public enum DominantHand {
		RIGHT, LEFT, AMBIDEXTROUS;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String prefix;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "middle_name")
	private String middleName;
	
	@NotNull
	@Column(name = "last_name")
	private String lastName;
	
	private String suffix;

	private String nickname;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "dominant_hand")
	private Person.DominantHand dominantHand;	
	
	@Column(name = "parental_release")
	private boolean isParentReleased = false;
	
	@Column(name = "parent_release_sent_on")
	private LocalDate parentReleaseSentOn;
	
	@Column(name = "parent_release_received_on")
	private LocalDate parentReleaseReceivedOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate birthdate;
	
	private String photo;	
	private String college;
	
	@Column(name = "college_url")
	private String collegeUrl;
	
	@Column(name = "created_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	@Column(name = "modified_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne
	@JoinColumn(name = "modified_by")
	private User modifiedBy;
	
	@OneToMany
	@JoinColumn(name = "parent_id")
	private Set<Relationship> childRelationships = new HashSet<Relationship>();
	
	@OneToMany
	@JoinColumn(name = "child_id")
	private Set<Relationship> parentRelationships = new HashSet<Relationship>();
	
	@OneToMany(mappedBy = "person")
	private Set<PersonEvent> attendedEvents = new HashSet<PersonEvent>();
	
	@OneToMany(mappedBy = "person")
	private Set<PersonSeason> playedSeasons = new HashSet<PersonSeason>();
	
	//---------- Getter/Setters ----------//
	
	public UUID getId() {
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Set<PersonEvent> getAttendedEvents() {
		return attendedEvents;
	}

	public Set<PersonSeason> getPlayedSeasons() {
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
