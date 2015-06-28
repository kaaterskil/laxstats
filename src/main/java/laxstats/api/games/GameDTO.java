package laxstats.api.games;

import laxstats.api.sites.SiteAlignment;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class GameDTO {
	private final String id;
	private final SiteEntry site;
	private final TeamSeasonEntry teamOne;
	private final TeamSeasonEntry teamTwo;
	private final Alignment teamOneAlignment;
	private final Alignment teamTwoAlignment;
	private final SiteAlignment alignment;
	private final LocalDateTime startsAt;
	private final Schedule schedule;
	private final Status status;
	private final Conditions conditions;
	private final String description;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public GameDTO(String id, SiteEntry site, TeamSeasonEntry teamOne,
			TeamSeasonEntry teamTwo, Alignment teamOneAlignment,
			Alignment teamTwoAlignment, SiteAlignment alignment,
			LocalDateTime startsAt, Schedule schedule, Status status,
			Conditions conditions, String description, LocalDateTime createdAt,
			UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.id = id;
		this.site = site;
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
		this.teamOneAlignment = teamOneAlignment;
		this.teamTwoAlignment = teamTwoAlignment;
		this.alignment = alignment;
		this.startsAt = startsAt;
		this.schedule = schedule;
		this.status = status;
		this.conditions = conditions;
		this.description = description;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public GameDTO(String id, SiteEntry site, TeamSeasonEntry teamOne,
			TeamSeasonEntry teamTwo, Alignment teamOneAlignment,
			Alignment teamTwoAlignment, SiteAlignment alignment,
			LocalDateTime startsAt, Schedule schedule, Status status,
			Conditions conditions, String description,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(id, site, teamOne, teamTwo, teamOneAlignment, teamTwoAlignment,
				alignment, startsAt, schedule, status, conditions, description,
				null, null, modifiedAt, modifiedBy);
	}

	public String getId() {
		return id;
	}

	public SiteEntry getSite() {
		return site;
	}

	public TeamSeasonEntry getTeamOne() {
		return teamOne;
	}

	public TeamSeasonEntry getTeamTwo() {
		return teamTwo;
	}

	public Alignment getTeamOneAlignment() {
		return teamOneAlignment;
	}

	public Alignment getTeamTwoAlignment() {
		return teamTwoAlignment;
	}

	public SiteAlignment getAlignment() {
		return alignment;
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public Status getStatus() {
		return status;
	}

	public Conditions getConditions() {
		return conditions;
	}

	public String getDescription() {
		return description;
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
