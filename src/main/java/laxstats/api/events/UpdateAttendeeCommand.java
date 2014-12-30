package laxstats.api.events;

public class UpdateAttendeeCommand extends AbstractEventCommand {
	private final AttendeeDTO attendeeDTO;

	public UpdateAttendeeCommand(EventId eventId, AttendeeDTO attendeeDTO) {
		super(eventId);
		this.attendeeDTO = attendeeDTO;
	}

	public AttendeeDTO getAttendeeDTO() {
		return attendeeDTO;
	}
}
