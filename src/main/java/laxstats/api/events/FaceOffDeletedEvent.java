package laxstats.api.events;

public class FaceOffDeletedEvent extends AbstractPlayEvent {

	public FaceOffDeletedEvent(EventId eventId, String playId) {
		super(eventId, playId, null);
	}
}
