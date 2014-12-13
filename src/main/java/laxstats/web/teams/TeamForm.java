package laxstats.web.teams;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.people.Gender;

public class TeamForm {
	@NotNull
	private String name;

	@NotNull
	private Gender gender;
	private String homeSiteId;

	@Size(min = 6)
	private String password;

	// ---------- Getter/Setters ----------//

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getHomeSiteId() {
		return homeSiteId;
	}

	public void setHomeSiteId(String homeSiteId) {
		this.homeSiteId = homeSiteId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
