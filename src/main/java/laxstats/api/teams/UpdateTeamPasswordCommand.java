package laxstats.api.teams;

public class UpdateTeamPasswordCommand extends AbstractTeamCommand {
	private final TeamDTO teamDTO;

	public UpdateTeamPasswordCommand(TeamId teamId, TeamDTO teamDTO) {
		super(teamId);
		this.teamDTO = teamDTO;
	}

	public TeamDTO getTeamDTO() {
		return teamDTO;
	}

}
