package laxstats.api.events;

public class DeleteEventCommand extends AbstractEventCommand {

	public DeleteEventCommand(EventId eventId) {
		super(eventId);
	}
}
