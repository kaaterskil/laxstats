package laxstats.web.teams;

import laxstats.domain.Gender;

public class TeamForm {
	private String name;
	private Gender gender;
	private String homeSiteId;
	private String password;
	
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
