package laxstats.api.leagues;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateLeagueCommand {
	@TargetAggregateIdentifier
	private final LeagueId leagueId;
	private final LeagueDTO leagueDTO;

	public UpdateLeagueCommand(LeagueId leagueId, LeagueDTO leagueDTO) {
		this.leagueId = leagueId;
		this.leagueDTO = leagueDTO;
	}

	public LeagueId getLeagueId() {
		return leagueId;
	}

	public LeagueDTO getLeagueDTO() {
		return leagueDTO;
	}
}
