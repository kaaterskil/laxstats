package laxstats.api.teamSeasons;


public class TeamSeasonCreatedEvent {
	private final TeamSeasonId teamSeasonId;
	private final TeamSeasonDTO teamSeasonDTO;

	public TeamSeasonCreatedEvent(TeamSeasonId teamSeasonId,
			TeamSeasonDTO teamSeasonDTO) {
		this.teamSeasonId = teamSeasonId;
		this.teamSeasonDTO = teamSeasonDTO;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public TeamSeasonDTO getTeamSeasonDTO() {
		return teamSeasonDTO;
	}
}
