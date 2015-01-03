package laxstats.api.events;


public class RecordShotCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordShotCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
