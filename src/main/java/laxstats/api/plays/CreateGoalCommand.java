package laxstats.api.plays;

public class CreateGoalCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public CreateGoalCommand(PlayId playId, PlayDTO playDTO) {
		super(playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
