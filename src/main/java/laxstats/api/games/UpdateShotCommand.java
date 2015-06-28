package laxstats.api.games;


public class UpdateShotCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateShotCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
