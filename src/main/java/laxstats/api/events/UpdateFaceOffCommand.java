package laxstats.api.events;


public class UpdateFaceOffCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateFaceOffCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
