package laxstats.api.games;


public class GroundBallRecordedEvent extends AbstractPlayEvent {

	public GroundBallRecordedEvent(GameId gameId, String playId,
			PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
