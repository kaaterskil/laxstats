package laxstats.api.plays;

public class ClearCreatedEvent extends AbstractPlayCreatedEvent {

	public ClearCreatedEvent(PlayId playId, PlayDTO playDTO) {
		super(playId, playDTO);
	}
}
