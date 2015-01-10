package laxstats.api.events;

public class DeletePenaltyCommand extends AbstractPlayCommand {

	public DeletePenaltyCommand(EventId eventId, String playId) {
		super(eventId, playId);
	}
}
