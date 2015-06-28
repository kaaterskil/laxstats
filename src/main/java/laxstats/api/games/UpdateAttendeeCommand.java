package laxstats.api.games;

public class UpdateAttendeeCommand extends AbstractEventCommand {
	private final AttendeeDTO attendeeDTO;

	public UpdateAttendeeCommand(GameId gameId, AttendeeDTO attendeeDTO) {
		super(gameId);
		this.attendeeDTO = attendeeDTO;
	}

	public AttendeeDTO getAttendeeDTO() {
		return attendeeDTO;
	}
}
