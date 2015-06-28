package laxstats.api.games;


public class FaceOffRecordedEvent extends AbstractPlayEvent {

	public FaceOffRecordedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
