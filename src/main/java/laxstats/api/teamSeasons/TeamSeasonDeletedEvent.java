package laxstats.api.teamSeasons;

public class TeamSeasonDeletedEvent {
	private final TeamSeasonId teamSeasonId;

	public TeamSeasonDeletedEvent(TeamSeasonId teamSeasonId) {
		this.teamSeasonId = teamSeasonId;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}
}
