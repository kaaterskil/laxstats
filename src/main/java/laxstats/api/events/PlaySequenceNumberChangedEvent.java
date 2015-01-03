package laxstats.api.events;

public class PlaySequenceNumberChangedEvent {
	private final EventId eventId;
	private final String playId;
	private final int sequenceNumber;

	public PlaySequenceNumberChangedEvent(EventId eventId, String playId,
			int sequenceNumber) {
		this.eventId = eventId;
		this.playId = playId;
		this.sequenceNumber = sequenceNumber;
	}

	public EventId getEventId() {
		return eventId;
	}

	public String getPlayId() {
		return playId;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}
}
