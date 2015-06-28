package laxstats.api.games;

public class ClearDeletedEvent extends AbstractPlayEvent {

	public ClearDeletedEvent(GameId gameId, String playId) {
		super(gameId, playId, null);
	}
}
