package laxstats.api.games;

public class DeleteAttendeeCommand extends AbstractEventCommand {
	private final String attendeeId;

	public DeleteAttendeeCommand(GameId gameId, String attendeeId) {
		super(gameId);
		this.attendeeId = attendeeId;
	}

	public String getAttendeeId() {
		return attendeeId;
	}
}
