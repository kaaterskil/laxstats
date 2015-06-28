package laxstats.api.games;

public class RegisterAttendeeCommand extends AbstractEventCommand {
	private final AttendeeDTO attendeeDTO;

	public RegisterAttendeeCommand(GameId gameId, AttendeeDTO attendeeDTO) {
		super(gameId);
		this.attendeeDTO = attendeeDTO;
	}

	public AttendeeDTO getAttendeeDTO() {
		return attendeeDTO;
	}
}
