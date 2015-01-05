package laxstats.api.events;


public class ShotUpdatedEvent extends AbstractPlayEvent {

	public ShotUpdatedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId, playDTO);
	}
}
