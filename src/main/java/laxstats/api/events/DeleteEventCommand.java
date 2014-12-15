package laxstats.api.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DeleteEventCommand {

	@TargetAggregateIdentifier
	private final EventId eventId;

	public DeleteEventCommand(EventId eventId) {
		this.eventId = eventId;
	}

	public EventId getEventId() {
		return eventId;
	}

}
