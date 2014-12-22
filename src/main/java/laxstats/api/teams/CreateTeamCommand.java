package laxstats.api.teams;

public class CreateTeamCommand extends AbstractTeamCommand {
	private final TeamDTO teamDTO;

	public CreateTeamCommand(TeamId teamId, TeamDTO teamDTO) {
		super(teamId);
		this.teamDTO = teamDTO;
	}

	public TeamDTO getTeamDTO() {
		return teamDTO;
	}

}
