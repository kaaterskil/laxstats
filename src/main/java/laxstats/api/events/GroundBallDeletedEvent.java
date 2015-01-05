package laxstats.api.events;

public class GroundBallDeletedEvent extends AbstractPlayEvent {

	public GroundBallDeletedEvent(EventId eventId, String playId) {
		super(eventId, playId, null);
	}
}
