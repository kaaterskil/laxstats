package laxstats.api.events;


public class RecordFaceoffCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordFaceoffCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
