package laxstats.api.events;


public class PenaltyRecordedEvent extends AbstractPlayEvent {

	public PenaltyRecordedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
