package laxstats.web.people;

import javax.validation.constraints.NotNull;

import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;

import org.joda.time.LocalDate;

public class PersonForm {

	public static String fullName(PersonForm form) {
		final StringBuffer buf = new StringBuffer();
		boolean needsSpace = false;

		if (form.prefix.length() > 0) {
			buf.append(form.prefix);
			needsSpace = true;
		}
		if (form.firstName.length() > 0) {
			if (needsSpace) {
				buf.append(" ");
			}
			buf.append(form.firstName);
			needsSpace = true;
		}
		if (form.middleName.length() > 0) {
			if (needsSpace) {
				buf.append(" ");
			}
			buf.append(form.middleName);
			needsSpace = true;
		}
		if (form.lastName.length() > 0) {
			if (needsSpace) {
				buf.append(" ");
			}
			buf.append(form.lastName);
			needsSpace = true;
		}
		if (form.suffix.length() > 0) {
			if (needsSpace) {
				buf.append(" ");
			}
			buf.append(form.suffix);
		}
		return buf.toString();
	}

	private String prefix;
	private String firstName;
	private String middleName;

	@NotNull
	private String lastName;
	private String suffix;
	private String nickname;

	@NotNull
	private Gender gender;
	private DominantHand dominantHand;
	private LocalDate birthdate;

	// ---------- Getter / Setters ----------//

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public DominantHand getDominantHand() {
		return dominantHand;
	}

	public String getFirstName() {
		return firstName;
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

	public String getNickname() {
		return nickname;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public void setDominantHand(DominantHand dominantHand) {
		this.dominantHand = dominantHand;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
