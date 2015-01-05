package laxstats.api.events;


public class GoalUpdatedEvent extends AbstractPlayEvent {

	public GoalUpdatedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
