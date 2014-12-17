package laxstats.api.leagues;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DeleteLeagueCommand {
	@TargetAggregateIdentifier
	private final LeagueId leagueId;

	public DeleteLeagueCommand(LeagueId leagueId) {
		this.leagueId = leagueId;
	}

	public LeagueId getLeagueId() {
		return leagueId;
	}
}
