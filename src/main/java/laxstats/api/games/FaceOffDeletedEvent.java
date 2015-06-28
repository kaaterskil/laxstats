package laxstats.api.games;

public class FaceOffDeletedEvent extends AbstractPlayEvent {

	public FaceOffDeletedEvent(GameId gameId, String playId) {
		super(gameId, playId, null);
	}
}
