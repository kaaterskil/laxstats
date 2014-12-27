package laxstats.api.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;

public class TeamSeasonEditedEvent {
	private final TeamId teamId;
	private final TeamSeasonDTO teamSeasonDTO;

	public TeamSeasonEditedEvent(TeamId teamId, TeamSeasonDTO teamSeasonDTO) {
		this.teamId = teamId;
		this.teamSeasonDTO = teamSeasonDTO;
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public TeamSeasonDTO getTeamSeasonDTO() {
		return teamSeasonDTO;
	}
}
