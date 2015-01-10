package laxstats.api.events;

public class PenaltyDeletedEvent extends AbstractPlayEvent {

	public PenaltyDeletedEvent(EventId eventId, String playId) {
		super(eventId, playId, null);
	}
}
