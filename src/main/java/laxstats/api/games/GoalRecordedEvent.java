package laxstats.api.games;


public class GoalRecordedEvent extends AbstractPlayEvent {

	public GoalRecordedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
