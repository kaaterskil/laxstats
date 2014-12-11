package laxstats.api.plays;

import laxstats.api.AggregateId;

public class PlayId extends AggregateId {
	private static final long serialVersionUID = -5841384211764341049L;

	public PlayId() {
		super();
	}

	public PlayId(String identifier) {
		super(identifier);
	}
}
