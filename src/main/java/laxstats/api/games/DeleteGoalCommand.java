package laxstats.api.games;

public class DeleteGoalCommand extends AbstractPlayCommand {

	public DeleteGoalCommand(GameId gameId, String playId) {
		super(gameId, playId);
	}
}
