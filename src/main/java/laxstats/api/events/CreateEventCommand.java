package laxstats.api.events;

public class CreateEventCommand extends AbstractEventCommand {

	public CreateEventCommand(EventId eventId, EventDTO eventDTO) {
		super(eventId, eventDTO);
	}

}
