package laxstats.api.teams;

import laxstats.api.people.Gender;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class TeamDTO {
	private final TeamId teamId;
	private final String name;
	private final Gender gender;
	private final SiteEntry homeSite;
	private final String encodedPassword;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public TeamDTO(TeamId teamId, String name, Gender gender,
			SiteEntry homeSite, LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(teamId, name, gender, homeSite, null, createdAt, createdBy,
				modifiedAt, modifiedBy);
	}

	public TeamDTO(TeamId teamId, String name, Gender gender,
			SiteEntry homeSite, LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(teamId, name, gender, homeSite, null, null, null, modifiedAt,
				modifiedBy);
	}

	public TeamDTO(TeamId teamId, String encodedPassword,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(teamId, null, null, null, encodedPassword, null, null, modifiedAt,
				modifiedBy);
	}

	protected TeamDTO(TeamId teamId, String name, Gender gender,
			SiteEntry homeSite, String encodedPassword,
			LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.teamId = teamId;
		this.name = name;
		this.gender = gender;
		this.homeSite = homeSite;
		this.encodedPassword = encodedPassword;
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

	public SiteEntry getHomeSite() {
		return homeSite;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}
}
