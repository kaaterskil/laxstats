package laxstats.api.events;

import laxstats.api.plays.PlayId;

public class ClearCreatedEvent extends AbstractPlayEvent {

	protected ClearCreatedEvent(EventId eventId, PlayId playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
