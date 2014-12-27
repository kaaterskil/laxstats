package laxstats.api.teamSeasons;

public class TeamSeasonUpdatedEvent {
	private final TeamSeasonId identifier;
	private final TeamSeasonDTO teamSeasonDTO;

	public TeamSeasonUpdatedEvent(TeamSeasonId identifier,
			TeamSeasonDTO teamSeasonDTO) {
		this.identifier = identifier;
		this.teamSeasonDTO = teamSeasonDTO;
	}

	public TeamSeasonId getIdentifier() {
		return identifier;
	}

	public TeamSeasonDTO getTeamSeasonDTO() {
		return teamSeasonDTO;
	}
}
