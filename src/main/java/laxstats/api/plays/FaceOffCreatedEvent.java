package laxstats.api.plays;

public class FaceOffCreatedEvent extends AbstractPlayCreatedEvent {

	public FaceOffCreatedEvent(PlayId playId, PlayDTO playDTO) {
		super(playId, playDTO);
	}
}
