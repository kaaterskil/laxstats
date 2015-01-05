package laxstats.api.events;


public class GroundBallUpdatedEvent extends AbstractPlayEvent {

	public GroundBallUpdatedEvent(EventId eventId, String playId,
			PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
