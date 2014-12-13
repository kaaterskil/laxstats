package laxstats.api.teams;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DeleteTeamCommand {

	@TargetAggregateIdentifier
	private final TeamId teamId;

	public DeleteTeamCommand(TeamId teamId) {
		this.teamId = teamId;
	}

	public TeamId getTeamId() {
		return teamId;
	}
}
