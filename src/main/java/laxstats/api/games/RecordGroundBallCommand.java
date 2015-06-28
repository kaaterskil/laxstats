package laxstats.api.games;


public class RecordGroundBallCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordGroundBallCommand(GameId gameId, String playId,
			PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
