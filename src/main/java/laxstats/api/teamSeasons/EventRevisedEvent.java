package laxstats.api.teamSeasons;

import laxstats.api.events.EventDTO;

public class EventRevisedEvent {
	private final TeamSeasonId teamSeasonId;
	private final EventDTO eventDTO;

	public EventRevisedEvent(TeamSeasonId teamSeasonId, EventDTO eventDTO) {
		this.teamSeasonId = teamSeasonId;
		this.eventDTO = eventDTO;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public EventDTO getEventDTO() {
		return eventDTO;
	}
}
