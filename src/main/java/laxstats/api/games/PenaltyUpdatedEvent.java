package laxstats.api.games;


public class PenaltyUpdatedEvent extends AbstractPlayEvent {

	public PenaltyUpdatedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
