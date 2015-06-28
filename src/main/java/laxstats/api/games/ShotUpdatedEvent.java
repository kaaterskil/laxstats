package laxstats.api.games;


public class ShotUpdatedEvent extends AbstractPlayEvent {

	public ShotUpdatedEvent(GameId gameId, String playId, PlayDTO playDTO) {
		super(gameId, playId, playDTO);
	}
}
