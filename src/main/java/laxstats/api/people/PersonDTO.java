package laxstats.api.people;

import laxstats.domain.DominantHand;
import laxstats.domain.Gender;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class PersonDTO {
	private final PersonId personId;
	private final String prefix;
	private final String firstName;
	private final String middleName;
	private final String lastName;
	private final String suffix;
	private final String nickname;
	private final String fullName;
	private final Gender gender;
	private final DominantHand dominantHand;
	private final boolean isParentReleased;
	private final LocalDate parentReleaseSentOn;
	private final LocalDate parentReleaseReceivedOn;
	private final LocalDate birthdate;
	private final String photo;
	private final String college;
	private final String collegeUrl;
	private final String createdBy;
	private final LocalDateTime createdAt;
	private final String modifiedBy;
	private final LocalDateTime modifiedAt;

	public PersonDTO(PersonId personId, String prefix, String firstName, String middleName, String lastName, String suffix, String nickname, String fullName, Gender gender, DominantHand dominantHand, boolean isParentReleased, LocalDate parentReleaseSentOn, LocalDate parentReleaseReceivedOn, LocalDate birthdate, String photo, String college, String collegeUrl, String createdBy, LocalDateTime createdAt, String modifiedBy, LocalDateTime modifiedAt) {
		this.personId = personId;
		this.prefix = prefix;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.suffix = suffix;
		this.nickname = nickname;
		this.fullName = fullName;
		this.gender = gender;
		this.dominantHand = dominantHand;
		this.isParentReleased = isParentReleased;
		this.parentReleaseSentOn = parentReleaseSentOn;
		this.parentReleaseReceivedOn = parentReleaseReceivedOn;
		this.birthdate = birthdate;
		this.photo = photo;
		this.college = college;
		this.collegeUrl = collegeUrl;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.modifiedBy = modifiedBy;
		this.modifiedAt = modifiedAt;
	}

	public PersonId getPersonId() {
		return personId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}
}
