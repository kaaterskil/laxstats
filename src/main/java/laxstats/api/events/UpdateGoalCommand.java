package laxstats.api.events;


public class UpdateGoalCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateGoalCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
