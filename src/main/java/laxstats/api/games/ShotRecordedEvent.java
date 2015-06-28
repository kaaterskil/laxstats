package laxstats.api.games;


public class ShotRecordedEvent extends AbstractPlayEvent {

	public ShotRecordedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
