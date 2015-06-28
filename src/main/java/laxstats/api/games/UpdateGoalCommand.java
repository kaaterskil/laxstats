package laxstats.api.games;


public class UpdateGoalCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateGoalCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
