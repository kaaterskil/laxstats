package laxstats.api.games;

public class DeleteShotCommand extends AbstractPlayCommand {

	public DeleteShotCommand(GameId gameId, String playId) {
		super(gameId, playId);
	}
}
