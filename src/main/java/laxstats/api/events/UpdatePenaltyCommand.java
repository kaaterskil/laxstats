package laxstats.api.events;


public class UpdatePenaltyCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdatePenaltyCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
