package laxstats.api.plays;

public class ShotCreatedEvent extends AbstractPlayCreatedEvent {

	public ShotCreatedEvent(PlayId playId, PlayDTO playDTO) {
		super(playId, playDTO);
	}
}
