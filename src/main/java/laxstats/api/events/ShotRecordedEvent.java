package laxstats.api.events;

import laxstats.api.plays.PlayId;

public class ShotRecordedEvent extends AbstractPlayEvent {

	public ShotRecordedEvent(EventId eventId, PlayId playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
