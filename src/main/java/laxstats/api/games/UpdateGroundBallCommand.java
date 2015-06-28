package laxstats.api.games;


public class UpdateGroundBallCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateGroundBallCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
