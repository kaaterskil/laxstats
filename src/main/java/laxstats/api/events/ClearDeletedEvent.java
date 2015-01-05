package laxstats.api.events;

public class ClearDeletedEvent extends AbstractPlayEvent {

	public ClearDeletedEvent(EventId eventId, String playId) {
		super(eventId, playId, null);
	}
}
