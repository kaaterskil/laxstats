package laxstats.api.games;


public class RecordShotCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordShotCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
