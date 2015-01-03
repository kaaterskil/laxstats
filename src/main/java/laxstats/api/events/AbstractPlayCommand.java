package laxstats.api.events;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

abstract public class AbstractPlayCommand {
	@TargetAggregateIdentifier
	protected EventId eventId;
	protected String playId;

	protected AbstractPlayCommand(EventId eventId, String playId) {
		this.eventId = eventId;
		this.playId = playId;
	}

	public EventId getEventId() {
		return eventId;
	}

	public String getPlayId() {
		return playId;
	}
}
