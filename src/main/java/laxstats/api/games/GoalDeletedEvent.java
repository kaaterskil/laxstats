package laxstats.api.games;

public class GoalDeletedEvent extends AbstractPlayEvent {

	public GoalDeletedEvent(GameId gameId, String playId) {
		super(gameId, playId, null);
	}
}
