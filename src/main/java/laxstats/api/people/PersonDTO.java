package laxstats.api.people;

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

	public PersonDTO(PersonId personId, String prefix, String firstName,
			String middleName, String lastName, String suffix, String nickname,
			String fullName, Gender gender, DominantHand dominantHand,
			boolean isParentReleased, LocalDate parentReleaseSentOn,
			LocalDate parentReleaseReceivedOn, LocalDate birthdate,
			String photo, String college, String collegeUrl, String createdBy,
			LocalDateTime createdAt, String modifiedBy, LocalDateTime modifiedAt) {
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

	public String fullName() {
		final StringBuilder sb = new StringBuilder();
		boolean needsSpace = false;

		if (prefix.length() > 0) {
			sb.append(prefix);
			needsSpace = true;
		}
		if (firstName.length() > 0) {
			if (needsSpace) {
				sb.append(" ");
			}
			sb.append(firstName);
			needsSpace = true;
		}
		if (middleName.length() > 0) {
			if (needsSpace) {
				sb.append(" ");
			}
			sb.append(middleName);
			needsSpace = true;
		}
		if (lastName.length() > 0) {
			if (needsSpace) {
				sb.append(" ");
			}
			sb.append(lastName);
			needsSpace = true;
		}
		if (suffix.length() > 0) {
			if (needsSpace) {
				sb.append(" ");
			}
			sb.append(suffix);
		}
		return sb.toString();
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public String getCollege() {
		return college;
	}

	public String getCollegeUrl() {
		return collegeUrl;
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

	public PersonId getPersonId() {
		return personId;
	}

	public String getPhoto() {
		return photo;
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
}
