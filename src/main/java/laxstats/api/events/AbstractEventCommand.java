package laxstats.api.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public abstract class AbstractEventCommand {
	@TargetAggregateIdentifier
	private final EventId eventId;

	protected AbstractEventCommand(EventId eventId) {
		this.eventId = eventId;
	}

	public EventId getEventId() {
		return eventId;
	}
}
