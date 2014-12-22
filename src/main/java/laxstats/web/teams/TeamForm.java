package laxstats.web.teams;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.people.Gender;

public class TeamForm {
	@NotNull
	private String name;

	@NotNull
	private Gender gender;
	private String siteId;
	private Map<String, String> sites = new HashMap<>();

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

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Map<String, String> getSites() {
		return sites;
	}

	public void setSites(Map<String, String> sites) {
		this.sites = sites;
	}
}
