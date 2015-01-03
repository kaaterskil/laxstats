package laxstats.api.events;

import laxstats.api.plays.PlayId;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

abstract public class AbstractPlayCommand {
	@TargetAggregateIdentifier
	protected EventId eventId;
	protected PlayId playId;

	protected AbstractPlayCommand(EventId eventId, PlayId playId) {
		this.eventId = eventId;
		this.playId = playId;
	}

	public EventId getEventId() {
		return eventId;
	}

	public PlayId getPlayId() {
		return playId;
	}
}
