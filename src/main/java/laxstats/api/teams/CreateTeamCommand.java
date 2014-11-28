package laxstats.api.teams;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateTeamCommand {

	@TargetAggregateIdentifier
	private final TeamId teamId;
	private final TeamDTO teamDTO;
	
	public CreateTeamCommand(TeamId teamId, TeamDTO teamDTO) {
		this.teamId = teamId;
		this.teamDTO = teamDTO;
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public TeamDTO getTeamDTO() {
		return teamDTO;
	}
	
}
