package laxstats.api.teams;

public class CreateTeamPasswordCommand extends AbstractTeamCommand {
	private final TeamDTO teamDTO;

	public CreateTeamPasswordCommand(TeamId teamId, TeamDTO teamDTO) {
		super(teamId);
		this.teamDTO = teamDTO;
	}

	public TeamDTO getTeamDTO() {
		return teamDTO;
	}

}
