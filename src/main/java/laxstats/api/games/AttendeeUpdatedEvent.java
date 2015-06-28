package laxstats.api.games;

public class AttendeeUpdatedEvent {
	private final GameId gameId;
	private final AttendeeDTO attendeeDTO;

	public AttendeeUpdatedEvent(GameId gameId, AttendeeDTO attendeeDTO) {
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
