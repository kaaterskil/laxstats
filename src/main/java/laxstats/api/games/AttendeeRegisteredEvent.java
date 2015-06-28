package laxstats.api.games;

public class AttendeeRegisteredEvent {
	private final GameId gameId;
	private final AttendeeDTO attendeeDTO;

	public AttendeeRegisteredEvent(GameId gameId, AttendeeDTO attendeeDTO) {
		this.gameId = gameId;
		this.attendeeDTO = attendeeDTO;
	}

	public GameId getEventId() {
		return gameId;
	}

	public AttendeeDTO getAttendeeDTO() {
		return attendeeDTO;
	}
}
