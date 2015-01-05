package laxstats.api.events;


public class ClearUpdatedEvent extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public ClearUpdatedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
