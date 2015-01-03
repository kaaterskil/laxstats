package laxstats.api.events;


public class RecordClearCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordClearCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
