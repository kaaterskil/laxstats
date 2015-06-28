package laxstats.api.teamSeasons;

import laxstats.api.games.GameDTO;

public class EventRevisedEvent {
	private final TeamSeasonId teamSeasonId;
	private final GameDTO gameDTO;

	public EventRevisedEvent(TeamSeasonId teamSeasonId, GameDTO gameDTO) {
		this.teamSeasonId = teamSeasonId;
		this.gameDTO = gameDTO;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public GameDTO getEventDTO() {
		return gameDTO;
	}
}
