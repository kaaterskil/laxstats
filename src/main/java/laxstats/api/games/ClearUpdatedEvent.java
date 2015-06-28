package laxstats.api.games;


public class ClearUpdatedEvent extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public ClearUpdatedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
