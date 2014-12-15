package laxstats.api.plays;

public class CreateGroundBallCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public CreateGroundBallCommand(PlayId playId, PlayDTO playDTO) {
		super(playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
