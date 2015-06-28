package laxstats.api.games;


public class RecordPenaltyCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public RecordPenaltyCommand(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
