package laxstats.api.events;


public class FaceOffRecordedEvent extends AbstractPlayEvent {

	public FaceOffRecordedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
