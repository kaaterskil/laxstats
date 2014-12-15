package laxstats.api.events;

public class EventDeletedEvent {
	private final EventId eventId;

	public EventDeletedEvent(EventId eventId) {
		this.eventId = eventId;
	}

	public EventId getEventId() {
		return eventId;
	}
}
