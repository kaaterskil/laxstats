package laxstats.api.plays;

public class CreatePenaltyCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public CreatePenaltyCommand(PlayId playId, PlayDTO playDTO) {
		super(playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
