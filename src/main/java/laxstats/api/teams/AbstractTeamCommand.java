package laxstats.api.teams;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public abstract class AbstractTeamCommand {
	@TargetAggregateIdentifier
	private final TeamId teamId;

	public AbstractTeamCommand(TeamId teamId) {
		this.teamId = teamId;
	}

	public TeamId getTeamId() {
		return teamId;
	}
}
