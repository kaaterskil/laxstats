package laxstats.api.events;

public class UpdateEventCommand extends AbstractEventCommand {

	public UpdateEventCommand(EventId eventId, EventDTO eventDTO) {
		super(eventId, eventDTO);
	}

}
