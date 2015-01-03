package laxstats.api.events;


public class GoalRecordedEvent extends AbstractPlayEvent {

	public GoalRecordedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
