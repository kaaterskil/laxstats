package laxstats.api.events;


public class ShotRecordedEvent extends AbstractPlayEvent {

	public ShotRecordedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
