package laxstats.api.events;

import laxstats.api.plays.PlayId;

public class ClearRecordedEvent extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public ClearRecordedEvent(EventId eventId, PlayId playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
