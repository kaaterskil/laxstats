package laxstats.api.games;

public class PenaltyDeletedEvent extends AbstractPlayEvent {

	public PenaltyDeletedEvent(GameId gameId, String playId) {
		super(gameId, playId, null);
	}
}
