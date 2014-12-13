package laxstats.api.teams;

import laxstats.api.AggregateId;

public class TeamId extends AggregateId {
	private static final long serialVersionUID = -2148472630083353413L;

	public TeamId() {
		super();
	}

	public TeamId(String identifier) {
		super(identifier);
	}
}
