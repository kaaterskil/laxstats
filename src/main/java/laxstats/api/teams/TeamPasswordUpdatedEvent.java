package laxstats.api.teams;

public class TeamPasswordUpdatedEvent {
	private final TeamId teamId;
	private final TeamDTO teamDTO;
	
	public TeamPasswordUpdatedEvent(TeamId teamId, TeamDTO teamDTO) {
		this.teamId = teamId;
		this.teamDTO = teamDTO;
	}

	public TeamId getTeamId() {
		return teamId;
	}

	public TeamDTO getTeamDTO() {
		return teamDTO;
	}
	
}
