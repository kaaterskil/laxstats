package laxstats.api.plays;

public class CreateShotCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public CreateShotCommand(PlayId playId, PlayDTO playDTO) {
		super(playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
