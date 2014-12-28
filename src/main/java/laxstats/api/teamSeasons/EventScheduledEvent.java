package laxstats.api.teamSeasons;

import laxstats.api.events.EventDTO;

public class EventScheduledEvent {
	private final TeamSeasonId teamSeasonId;
	private final EventDTO event;

	public EventScheduledEvent(TeamSeasonId teamSeasonId, EventDTO event) {
		this.teamSeasonId = teamSeasonId;
		this.event = event;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public EventDTO getEvent() {
		return event;
	}
}
