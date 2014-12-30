package laxstats.api.events;

public class AttendeeDeletedEvent {
	private final EventId eventId;
	private final String attendeeId;

	public AttendeeDeletedEvent(EventId eventId, String attendeeId) {
		this.eventId = eventId;
		this.attendeeId = attendeeId;
	}

	public EventId getEventId() {
		return eventId;
	}

	public String getAttendeeId() {
		return attendeeId;
	}
}
