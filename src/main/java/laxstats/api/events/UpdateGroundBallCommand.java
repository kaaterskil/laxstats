package laxstats.api.events;


public class UpdateGroundBallCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateGroundBallCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
