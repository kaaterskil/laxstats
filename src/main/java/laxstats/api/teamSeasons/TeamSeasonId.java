package laxstats.api.teamSeasons;

import laxstats.api.AggregateId;

public class TeamSeasonId extends AggregateId {
	private static final long serialVersionUID = -5776121020631292936L;

	public TeamSeasonId() {
		super();
	}

	public TeamSeasonId(String identifier) {
		super(identifier);
	}
}
