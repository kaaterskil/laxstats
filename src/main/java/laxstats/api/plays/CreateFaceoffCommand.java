package laxstats.api.plays;

public class CreateFaceoffCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public CreateFaceoffCommand(PlayId playId, PlayDTO playDTO) {
		super(playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
