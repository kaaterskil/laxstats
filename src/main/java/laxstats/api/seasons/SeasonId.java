package laxstats.api.seasons;

import laxstats.api.AggregateId;

public class SeasonId extends AggregateId {
	private static final long serialVersionUID = 7511307761939198548L;

	public SeasonId() {
		super();
	}

	public SeasonId(String identifier){
		super(identifier);
	}
}
