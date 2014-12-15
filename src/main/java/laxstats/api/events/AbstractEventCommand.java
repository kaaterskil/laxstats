package laxstats.api.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public abstract class AbstractEventCommand {
	@TargetAggregateIdentifier
	private final EventId eventId;
	private final EventDTO eventDTO;

	protected AbstractEventCommand(EventId eventId, EventDTO eventDTO) {
		this.eventId = eventId;
		this.eventDTO = eventDTO;
	}

	public EventId getEventId() {
		return eventId;
	}

	public EventDTO getEventDTO() {
		return eventDTO;
	}
}
