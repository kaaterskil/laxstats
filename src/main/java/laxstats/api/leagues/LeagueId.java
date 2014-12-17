package laxstats.api.leagues;

import laxstats.api.AggregateId;

public class LeagueId extends AggregateId {
	private static final long serialVersionUID = -1063245718011007169L;

	public LeagueId() {
		super();
	}

	public LeagueId(String identifier) {
		super(identifier);
	}
}
