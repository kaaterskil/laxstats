package laxstats.api.events;

import laxstats.api.plays.PlayId;

public class GoalRecordedEvent extends AbstractPlayEvent {

	public GoalRecordedEvent(EventId eventId, PlayId playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
