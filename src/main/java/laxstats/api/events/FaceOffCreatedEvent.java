package laxstats.api.events;

import laxstats.api.plays.PlayId;

public class FaceOffCreatedEvent extends AbstractPlayEvent {

	public FaceOffCreatedEvent(EventId eventId, PlayId playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
