package laxstats.api.teams;

import laxstats.api.people.Gender;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class TeamDTO {
	private TeamId teamId;
	private String name;
	private Gender gender;
	private SiteEntry homeSite;
	private String encodedPassword;
	private LocalDateTime createdAt;
	private UserEntry createdBy;
	private LocalDateTime modifiedAt;
	private UserEntry modifiedBy;

	public TeamDTO() {
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public void setTeamId(TeamId teamId) {
		this.teamId = teamId;
	}

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

	public SiteEntry getHomeSite() {
		return homeSite;
	}

	public void setHomeSite(SiteEntry homeSite) {
		this.homeSite = homeSite;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
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

}
