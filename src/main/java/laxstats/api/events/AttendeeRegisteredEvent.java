package laxstats.api.events;

public class AttendeeRegisteredEvent {
	private final EventId eventId;
	private final AttendeeDTO attendeeDTO;

	public AttendeeRegisteredEvent(EventId eventId, AttendeeDTO attendeeDTO) {
		this.eventId = eventId;
		this.attendeeDTO = attendeeDTO;
	}

	public EventId getEventId() {
		return eventId;
	}

	public AttendeeDTO getAttendeeDTO() {
		return attendeeDTO;
	}
}
