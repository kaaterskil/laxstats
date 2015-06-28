package laxstats.api.games;


public class RecordFaceoffCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordFaceoffCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
