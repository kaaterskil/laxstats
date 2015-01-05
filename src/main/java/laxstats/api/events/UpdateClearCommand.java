package laxstats.api.events;


public class UpdateClearCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateClearCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
