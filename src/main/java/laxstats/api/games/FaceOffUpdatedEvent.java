package laxstats.api.games;


public class FaceOffUpdatedEvent extends AbstractPlayEvent {

	public FaceOffUpdatedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
