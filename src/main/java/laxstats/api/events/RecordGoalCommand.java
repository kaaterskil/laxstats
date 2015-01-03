package laxstats.api.events;


public class RecordGoalCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordGoalCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}