package laxstats.api.events;


public class RecordPenaltyCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordPenaltyCommand(EventId eventId, String playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
