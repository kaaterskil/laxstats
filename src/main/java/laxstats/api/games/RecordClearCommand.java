package laxstats.api.games;


public class RecordClearCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordClearCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
