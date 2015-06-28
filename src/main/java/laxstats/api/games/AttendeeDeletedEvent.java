package laxstats.api.games;

public class AttendeeDeletedEvent {
	private final GameId gameId;
	private final String attendeeId;

	public AttendeeDeletedEvent(GameId gameId, String attendeeId) {
		this.gameId = gameId;
		this.attendeeId = attendeeId;
	}

	public GameId getEventId() {
		return gameId;
	}

	public String getAttendeeId() {
		return attendeeId;
	}
}
