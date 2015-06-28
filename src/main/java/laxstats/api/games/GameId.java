package laxstats.api.games;

import laxstats.api.AggregateId;

public class GameId extends AggregateId {
	private static final long serialVersionUID = 923566508988353139L;

	public GameId() {
	}

	public GameId(String eventId) {
		super(eventId);
	}
}
