package laxstats.api.games;


public class GoalUpdatedEvent extends AbstractPlayEvent {

	public GoalUpdatedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
