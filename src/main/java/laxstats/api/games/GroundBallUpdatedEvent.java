package laxstats.api.games;


public class GroundBallUpdatedEvent extends AbstractPlayEvent {

	public GroundBallUpdatedEvent(GameId gameId, String playId,
			PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
