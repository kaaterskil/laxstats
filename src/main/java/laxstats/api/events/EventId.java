package laxstats.api.events;

import laxstats.api.AggregateId;

public class EventId extends AggregateId {
	private static final long serialVersionUID = 923566508988353139L;

	public EventId() {
	}

	public EventId(String eventId) {
		super(eventId);
	}
}
