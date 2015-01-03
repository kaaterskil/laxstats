package laxstats.api.events;


public class ClearRecordedEvent extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public ClearRecordedEvent(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
