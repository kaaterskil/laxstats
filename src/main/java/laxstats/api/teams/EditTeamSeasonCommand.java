package laxstats.api.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;

public class EditTeamSeasonCommand extends AbstractTeamCommand {
	private final TeamSeasonDTO teamSeasonDTO;

	public EditTeamSeasonCommand(TeamId teamId, TeamSeasonDTO teamSeasonDTO) {
		super(teamId);
		this.teamSeasonDTO = teamSeasonDTO;
	}

	public TeamSeasonDTO getTeamSeasonDTO() {
		return teamSeasonDTO;
	}
}
