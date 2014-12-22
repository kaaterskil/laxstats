package laxstats.api.teams;


public class UpdateTeamCommand extends AbstractTeamCommand {
	private final TeamDTO teamDTO;

	public UpdateTeamCommand(TeamId teamId, TeamDTO teamDTO) {
		super(teamId);
		this.teamDTO = teamDTO;
	}

	public TeamDTO getTeamDTO() {
		return teamDTO;
	}

}
