package laxstats.api.games;

public class DeletePenaltyCommand extends AbstractPlayCommand {

	public DeletePenaltyCommand(GameId gameId, String playId) {
		super(gameId, playId);
	}
}
