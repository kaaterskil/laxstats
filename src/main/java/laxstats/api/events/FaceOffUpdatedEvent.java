package laxstats.api.events;


public class FaceOffUpdatedEvent extends AbstractPlayEvent {

	public FaceOffUpdatedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
