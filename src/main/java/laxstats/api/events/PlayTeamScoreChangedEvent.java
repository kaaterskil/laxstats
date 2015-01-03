package laxstats.api.events;

public class PlayTeamScoreChangedEvent {
	private final EventId eventId;
	private final String playId;
	private final int teamScore;

	public PlayTeamScoreChangedEvent(EventId eventId, String playId,
			int teamScore) {
		this.eventId = eventId;
		this.playId = playId;
		this.teamScore = teamScore;
	}

	public EventId getEventId() {
		return eventId;
	}

	public String getPlayId() {
		return playId;
	}

	public int getTeamScore() {
		return teamScore;
	}
}
