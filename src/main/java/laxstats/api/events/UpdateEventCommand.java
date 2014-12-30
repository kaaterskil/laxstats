package laxstats.api.events;

public class UpdateEventCommand extends AbstractEventCommand {
	private final EventDTO eventDTO;

	public UpdateEventCommand(EventId eventId, EventDTO eventDTO) {
		super(eventId);
		this.eventDTO = eventDTO;
	}

	public EventDTO getEventDTO() {
		return eventDTO;
	}

}
