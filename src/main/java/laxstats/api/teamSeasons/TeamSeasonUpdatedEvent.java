package laxstats.api.teamSeasons;


public class TeamSeasonUpdatedEvent {
	private final TeamSeasonId teamSeasonId;
	private final TeamSeasonDTO teamSeasonDTO;

	public TeamSeasonUpdatedEvent(TeamSeasonId teamSeasonId,
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
