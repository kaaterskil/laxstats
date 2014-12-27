package laxstats.domain.teams;

import laxstats.api.teamSeasons.TeamStatus;

import org.joda.time.LocalDate;

public class TeamSeasonInfo {
	private final String id;
	private final String teamId;
	private final String seasonId;
	private final LocalDate startsOn;
	private final LocalDate endsOn;
	private final TeamStatus status;

	public TeamSeasonInfo(String id, String teamId, String seasonId,
			LocalDate startsOn, LocalDate endsOn, TeamStatus status) {
		this.id = id;
		this.teamId = teamId;
		this.seasonId = seasonId;
		this.startsOn = startsOn;
		this.endsOn = endsOn;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public String getTeamId() {
		return teamId;
	}

	public String getSeasonId() {
		return seasonId;
	}

	public LocalDate getStartsOn() {
		return startsOn;
	}

	public LocalDate getEndsOn() {
		return endsOn;
	}

	public TeamStatus getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TeamSeasonInfo) {
			final TeamSeasonInfo that = (TeamSeasonInfo) obj;
			return this.id.equals(that.id);
		}
		return false;
	}
}
