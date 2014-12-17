package laxstats.api.leagues;

import laxstats.domain.leagues.TeamInfo;

public class TeamRegisteredEvent {
	private final LeagueId leagueId;
	private final TeamInfo team;

	public TeamRegisteredEvent(LeagueId leagueId, TeamInfo team) {
		this.leagueId = leagueId;
		this.team = team;
	}

	public LeagueId getLeagueId() {
		return leagueId;
	}

	public TeamInfo getTeam() {
		return team;
	}
}
