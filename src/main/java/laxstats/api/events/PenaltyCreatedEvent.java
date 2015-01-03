package laxstats.api.events;

import laxstats.api.plays.PlayId;

public class PenaltyCreatedEvent extends AbstractPlayEvent {

	protected PenaltyCreatedEvent(EventId eventId, PlayId playId,
			PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
