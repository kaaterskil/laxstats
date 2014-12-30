package laxstats.api.events;

public class CreateEventCommand extends AbstractEventCommand {
	private final EventDTO eventDTO;

	public CreateEventCommand(EventId eventId, EventDTO eventDTO) {
		super(eventId);
		this.eventDTO = eventDTO;
	}

	public EventDTO getEventDTO() {
		return eventDTO;
	}

}
