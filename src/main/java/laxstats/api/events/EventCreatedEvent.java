package laxstats.api.events;

public class EventCreatedEvent {
	private final EventId eventId;
	private final EventDTO eventDTO;

	public EventCreatedEvent(EventId eventId, EventDTO eventDTO) {
		this.eventId = eventId;
		this.eventDTO = eventDTO;
	}

	public EventId getEventId() {
		return eventId;
	}

	public EventDTO getEventDTO() {
		return eventDTO;
	}
}
