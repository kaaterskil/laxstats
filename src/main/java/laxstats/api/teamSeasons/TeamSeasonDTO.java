package laxstats.api.teamSeasons;

import laxstats.query.seasons.SeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class TeamSeasonDTO {
	private final TeamSeasonId teamSeasonId;
	private final TeamEntry team;
	private final SeasonEntry season;
	private final LocalDate startsOn;
	private final LocalDate endsOnd;
	private final TeamStatus status;
	private final UserEntry createdBy;
	private final LocalDateTime createdAt;
	private final UserEntry modifiedBy;
	private final LocalDateTime modifiedAt;

	public TeamSeasonDTO(TeamSeasonId teamSeasonId, TeamEntry team,
			SeasonEntry season, LocalDate startsOn, LocalDate endsOnd,
			TeamStatus status, UserEntry createdBy, LocalDateTime createdAt,
			UserEntry modifiedBy, LocalDateTime modifiedAt) {
		this.teamSeasonId = teamSeasonId;
		this.team = team;
		this.season = season;
		this.startsOn = startsOn;
		this.endsOnd = endsOnd;
		this.status = status;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.modifiedBy = modifiedBy;
		this.modifiedAt = modifiedAt;
	}

	public TeamSeasonDTO(TeamSeasonId teamSeasonId, TeamEntry team,
			SeasonEntry season, LocalDate startsOn, LocalDate endsOnd,
			TeamStatus status, UserEntry modifiedBy, LocalDateTime modifiedAt) {
		this(teamSeasonId, team, season, startsOn, endsOnd, status, null, null,
				modifiedBy, modifiedAt);
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public SeasonEntry getSeason() {
		return season;
	}

	public LocalDate getStartsOn() {
		return startsOn;
	}

	public LocalDate getEndsOnd() {
		return endsOnd;
	}

	public TeamStatus getStatus() {
		return status;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}
}
