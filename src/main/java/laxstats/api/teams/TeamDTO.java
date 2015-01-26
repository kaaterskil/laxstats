package laxstats.api.teams;

import laxstats.api.Region;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class TeamDTO {
	private final TeamId teamId;
	private final String sponsor;
	private final String name;
	private final String abbreviation;
	private final TeamGender gender;
	private final Letter letter;
	private final Region region;
	private final LeagueEntry affiliation;
	private final SiteEntry homeSite;
	private final String encodedPassword;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public TeamDTO(TeamId teamId, String sponsor, String name,
			String abbreviation, TeamGender gender, Letter letter,
			Region region, LeagueEntry affiliation, SiteEntry homeSite,
			LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(teamId, sponsor, name, abbreviation, gender, letter, region,
				affiliation, homeSite, null, createdAt, createdBy, modifiedAt,
				modifiedBy);
	}

	public TeamDTO(TeamId teamId, String sponsor, String name,
			String abbreviation, TeamGender gender, Letter letter,
			Region region, LeagueEntry affiliation, SiteEntry homeSite,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(teamId, sponsor, name, abbreviation, gender, letter, region,
				affiliation, homeSite, null, null, null, modifiedAt, modifiedBy);
	}

	public TeamDTO(TeamId teamId, String encodedPassword,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(teamId, null, null, null, null, null, null, null, null,
				encodedPassword, null, null, modifiedAt, modifiedBy);
	}

	protected TeamDTO(TeamId teamId, String sponsor, String name,
			String abbreviation, TeamGender gender, Letter letter,
			Region region, LeagueEntry affiliation, SiteEntry homeSite,
			String encodedPassword, LocalDateTime createdAt,
			UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.teamId = teamId;
		this.sponsor = sponsor;
		this.name = name;
		this.abbreviation = abbreviation;
		this.gender = gender;
		this.letter = letter;
		this.region = region;
		this.affiliation = affiliation;
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

	public String getSponsor() {
		return sponsor;
	}

	public String getName() {
		return name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public TeamGender getGender() {
		return gender;
	}

	public Letter getLetter() {
		return letter;
	}

	public Region getRegion() {
		return region;
	}

	public LeagueEntry getAffiliation() {
		return affiliation;
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
