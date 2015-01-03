package laxstats.api.events;


public class RecordGroundBallCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordGroundBallCommand(EventId eventId, String playId,
			PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
