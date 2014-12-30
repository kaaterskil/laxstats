package laxstats.api.events;

public class RegisterAttendeeCommand extends AbstractEventCommand {
	private final AttendeeDTO attendeeDTO;

	public RegisterAttendeeCommand(EventId eventId, AttendeeDTO attendeeDTO) {
		super(eventId);
		this.attendeeDTO = attendeeDTO;
	}

	public AttendeeDTO getAttendeeDTO() {
		return attendeeDTO;
	}
}
