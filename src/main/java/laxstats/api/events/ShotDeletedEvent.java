package laxstats.api.events;

public class ShotDeletedEvent extends AbstractPlayEvent {

	public ShotDeletedEvent(EventId eventId, String playId) {
		super(eventId, playId, null);
	}
}
