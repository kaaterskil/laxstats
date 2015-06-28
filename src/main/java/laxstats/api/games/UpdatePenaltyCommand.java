package laxstats.api.games;


public class UpdatePenaltyCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public UpdatePenaltyCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
