package laxstats.api.games;


public class RecordGoalCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordGoalCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
