package laxstats.api.teamSeasons;

public class TeamSeasonCreatedEvent {
	private final TeamSeasonId identifier;
	private final TeamSeasonDTO teamSeasonDTO;

	public TeamSeasonCreatedEvent(TeamSeasonId identifier,
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
