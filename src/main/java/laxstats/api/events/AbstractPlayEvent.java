package laxstats.api.events;


public abstract class AbstractPlayEvent {
	protected EventId eventId;
	protected final String playId;
	protected final PlayDTO playDTO;

	protected AbstractPlayEvent(EventId eventId, String playId, PlayDTO playDTO) {
		this.eventId = eventId;
		this.playId = playId;
		this.playDTO = playDTO;
	}

	public EventId getEventId() {
		return eventId;
	}

	public String getPlayId() {
		return playId;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
