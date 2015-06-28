package laxstats.api.games;


public class ClearRecordedEvent extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public ClearRecordedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
