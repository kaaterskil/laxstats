package laxstats.api.teams;

public class TeamDeletedEvent {

	private final TeamId teamId;

	public TeamDeletedEvent(TeamId teamId) {
		this.teamId = teamId;
	}

	public TeamId getTeamId() {
		return teamId;
	}
}
