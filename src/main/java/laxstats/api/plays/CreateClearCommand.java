package laxstats.api.plays;

public class CreateClearCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public CreateClearCommand(PlayId playId, PlayDTO playDTO) {
		super(playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
