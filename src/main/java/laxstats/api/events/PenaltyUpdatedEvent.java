package laxstats.api.events;


public class PenaltyUpdatedEvent extends AbstractPlayEvent {

	public PenaltyUpdatedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
