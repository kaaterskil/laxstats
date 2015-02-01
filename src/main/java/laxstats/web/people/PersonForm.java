package laxstats.web.people;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class PersonForm {

	public static String fullName(PersonForm form) {
		final StringBuilder sb = new StringBuilder();
		boolean needsSpace = false;

		if (form.prefix.length() > 0) {
			sb.append(form.prefix);
			needsSpace = true;
		}
		if (form.firstName.length() > 0) {
			if (needsSpace) {
				sb.append(" ");
			}
			sb.append(form.firstName);
			needsSpace = true;
		}
		if (form.middleName.length() > 0) {
			if (needsSpace) {
				sb.append(" ");
			}
			sb.append(form.middleName);
			needsSpace = true;
		}
		if (form.lastName.length() > 0) {
			if (needsSpace) {
				sb.append(" ");
			}
			sb.append(form.lastName);
			needsSpace = true;
		}
		if (form.suffix.length() > 0) {
			if (needsSpace) {
				sb.append(" ");
			}
			sb.append(form.suffix);
		}
		return sb.toString();
	}

	/*---------- Properties ----------*/

	@Size(max = 10)
	private String prefix;

	@Size(max = 20)
	private String firstName;

	@Size(max = 20)
	private String middleName;

	@NotNull
	@Size(min = 2, max = 30)
	private String lastName;

	@Size(max = 10)
	private String suffix;

	@Size(max = 20)
	private String nickname;

	private Gender gender;
	private DominantHand dominantHand;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdate;

	private boolean released;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate parentReleaseSentOn;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate parentReleaseReceivedOn;

	@Size(max = 100)
	private String college;

	List<Gender> genders;
	List<DominantHand> dominantHands;

	/*---------- Getter/Setters ----------*/

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
		return PersonForm.fullName(this);
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

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
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

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public List<Gender> getGenders() {
		if (genders == null) {
			genders = Arrays.asList(Gender.values());
		}
		return genders;
	}

	public List<DominantHand> getDominantHands() {
		if (dominantHands == null) {
			dominantHands = Arrays.asList(DominantHand.values());
		}
		return dominantHands;
	}

}
