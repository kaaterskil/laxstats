package laxstats.api.teams;

public class TeamPasswordCreatedEvent {
	private final TeamId teamId;
	private final TeamDTO teamDTO;
	
	public TeamPasswordCreatedEvent(TeamId teamId, TeamDTO teamDTO) {
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
