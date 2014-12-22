package laxstats.api.teamSeasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DeleteTeamSeasonCommand {
	@TargetAggregateIdentifier
	private final TeamSeasonId teamSeasonId;

	public DeleteTeamSeasonCommand(TeamSeasonId teamSeasonId) {
		this.teamSeasonId = teamSeasonId;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}
}
