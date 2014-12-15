package laxstats.api.plays;

public class PenaltyCreatedEvent extends AbstractPlayCreatedEvent {

	public PenaltyCreatedEvent(PlayId playId, PlayDTO playDTO) {
		super(playId, playDTO);
	}
}
