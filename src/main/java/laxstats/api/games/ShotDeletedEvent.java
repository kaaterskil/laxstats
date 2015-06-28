package laxstats.api.games;

public class ShotDeletedEvent extends AbstractPlayEvent {

	public ShotDeletedEvent(GameId gameId, String playId) {
		super(gameId, playId, null);
	}
}
