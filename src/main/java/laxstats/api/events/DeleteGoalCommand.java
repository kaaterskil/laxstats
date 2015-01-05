package laxstats.api.events;

public class DeleteGoalCommand extends AbstractPlayCommand {

	public DeleteGoalCommand(EventId eventId, String playId) {
		super(eventId, playId);
	}
}
