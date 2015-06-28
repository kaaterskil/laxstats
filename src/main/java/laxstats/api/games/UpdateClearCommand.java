package laxstats.api.games;


public class UpdateClearCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateClearCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
