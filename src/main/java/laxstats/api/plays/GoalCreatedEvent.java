package laxstats.api.plays;

public class GoalCreatedEvent extends AbstractPlayCreatedEvent {

	public GoalCreatedEvent(PlayId playId, PlayDTO playDTO) {
		super(playId, playDTO);
	}
}
