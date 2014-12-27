package laxstats.api.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;

public class TeamSeasonRegisteredEvent {
	private final TeamId teamId;
	private final TeamSeasonDTO teamSeasonDTO;

	public TeamSeasonRegisteredEvent(TeamId teamId, TeamSeasonDTO teamSeasonDTO) {
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
