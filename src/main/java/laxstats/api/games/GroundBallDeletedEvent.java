package laxstats.api.games;

public class GroundBallDeletedEvent extends AbstractPlayEvent {

	public GroundBallDeletedEvent(GameId gameId, String playId) {
		super(gameId, playId, null);
	}
}
