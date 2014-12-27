package laxstats.api.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;

public class RegisterTeamSeasonCommand extends AbstractTeamCommand {
	private final TeamSeasonDTO teamSeasonDTO;

	public RegisterTeamSeasonCommand(TeamId teamId, TeamSeasonDTO teamSeasonDTO) {
		super(teamId);
		this.teamSeasonDTO = teamSeasonDTO;
	}

	public TeamSeasonDTO getTeamSeasonDTO() {
		return teamSeasonDTO;
	}
}
