package laxstats.api.plays;

public class GroundBallCreatedEvent extends AbstractPlayCreatedEvent {

	public GroundBallCreatedEvent(PlayId playId, PlayDTO playDTO) {
		super(playId, playDTO);
	}
}
