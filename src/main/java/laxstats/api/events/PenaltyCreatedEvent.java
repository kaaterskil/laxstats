package laxstats.api.events;


public class PenaltyCreatedEvent extends AbstractPlayEvent {

	protected PenaltyCreatedEvent(EventId eventId, String playId,
			PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
