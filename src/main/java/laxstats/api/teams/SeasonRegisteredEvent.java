package laxstats.api.teams;

import laxstats.domain.teams.TeamSeasonInfo;

public class SeasonRegisteredEvent {
	private final TeamId teamId;
	private final TeamSeasonInfo season;

	public SeasonRegisteredEvent(TeamId teamId, TeamSeasonInfo season) {
		this.teamId = teamId;
		this.season = season;
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public TeamSeasonInfo getSeason() {
		return season;
	}
}
