package laxstats.api.events;

public class GoalDeletedEvent extends AbstractPlayEvent {

	public GoalDeletedEvent(EventId eventId, String playId) {
		super(eventId, playId, null);
	}
}
