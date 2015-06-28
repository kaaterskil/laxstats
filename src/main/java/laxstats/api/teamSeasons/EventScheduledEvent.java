package laxstats.api.teamSeasons;

import laxstats.api.games.GameDTO;

public class EventScheduledEvent {
	private final TeamSeasonId teamSeasonId;
	private final GameDTO event;

	public EventScheduledEvent(TeamSeasonId teamSeasonId, GameDTO event) {
		this.teamSeasonId = teamSeasonId;
		this.event = event;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public GameDTO getEvent() {
		return event;
	}
}
