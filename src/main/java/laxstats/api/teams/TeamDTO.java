package laxstats.api.teams;

import org.joda.time.LocalDateTime;

import laxstats.api.people.Gender;

public class TeamDTO {
	private final TeamId teamId;
	private final String name;
	private final Gender gender;
	private final String homeSiteId;
	private final String encryptedPassword;
	private final LocalDateTime createdAt;
	private final String createdBy;
	private final LocalDateTime modifiedAt;
	private final String modifiedBy;
	
	public TeamDTO(TeamId teamId, String name, Gender gender,
			String homeSiteId, String encryptedPassword,
			LocalDateTime createdAt, String createdBy,
			LocalDateTime modifiedAt, String modifiedBy) {
		this.teamId = teamId;
		this.name = name;
		this.gender = gender;
		this.homeSiteId = homeSiteId;
		this.encryptedPassword = encryptedPassword;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public String getName() {
		return name;
	}

	public Gender getGender() {
		return gender;
	}

	public String getHomeSiteId() {
		return homeSiteId;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}
	
}
