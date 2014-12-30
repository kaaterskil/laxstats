package laxstats.api.events;

public class DeleteAttendeeCommand extends AbstractEventCommand {
	private final String attendeeId;

	public DeleteAttendeeCommand(EventId eventId, String attendeeId) {
		super(eventId);
		this.attendeeId = attendeeId;
	}

	public String getAttendeeId() {
		return attendeeId;
	}
}
