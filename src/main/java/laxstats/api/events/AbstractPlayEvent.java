package laxstats.api.events;

import laxstats.api.plays.PlayId;

public abstract class AbstractPlayEvent {
	protected EventId eventId;
	protected final PlayId playId;
	protected final PlayDTO playDTO;

	protected AbstractPlayEvent(EventId eventId, PlayId playId,
			PlayDTO playDTO) {
		this.eventId = eventId;
		this.playId = playId;
		this.playDTO = playDTO;
	}

	public EventId getEventId() {
		return eventId;
	}

	public PlayId getPlayId() {
		return playId;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
