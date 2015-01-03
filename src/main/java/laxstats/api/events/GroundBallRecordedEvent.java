package laxstats.api.events;


public class GroundBallRecordedEvent extends AbstractPlayEvent {

	public GroundBallRecordedEvent(EventId eventId, String playId,
			PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
