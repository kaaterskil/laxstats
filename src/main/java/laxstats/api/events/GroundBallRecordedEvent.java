package laxstats.api.events;

import laxstats.api.plays.PlayId;

public class GroundBallRecordedEvent extends AbstractPlayEvent {

	public GroundBallRecordedEvent(EventId eventId, PlayId playId,
			PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
