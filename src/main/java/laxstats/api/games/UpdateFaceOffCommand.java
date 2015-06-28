package laxstats.api.games;


public class UpdateFaceOffCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdateFaceOffCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
