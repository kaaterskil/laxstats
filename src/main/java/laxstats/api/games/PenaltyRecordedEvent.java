package laxstats.api.games;


public class PenaltyRecordedEvent extends AbstractPlayEvent {

	public PenaltyRecordedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
