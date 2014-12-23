package laxstats.api.players;

import laxstats.api.AggregateId;

public class PlayerId extends AggregateId {
	private static final long serialVersionUID = 8363684825886208308L;

	public PlayerId() {
		super();
	}

	public PlayerId(String identifier) {
		super(identifier);
	}
}
