package laxstats.api.events;

public class DeleteShotCommand extends AbstractPlayCommand {

	public DeleteShotCommand(EventId eventId, String playId) {
		super(eventId, playId);
	}
}
