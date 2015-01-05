package laxstats.api.events;


public class UpdateShotCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateShotCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
