package laxstats.api.leagues;

import laxstats.domain.leagues.TeamInfo;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class RegisterTeamCommand {
	@TargetAggregateIdentifier
	private final LeagueId leagueId;
	private final TeamInfo team;

	public RegisterTeamCommand(LeagueId leagueId, TeamInfo team) {
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
