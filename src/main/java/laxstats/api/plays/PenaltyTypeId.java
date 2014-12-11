package laxstats.api.plays;

import laxstats.api.AggregateId;

public class PenaltyTypeId extends AggregateId {
	private static final long serialVersionUID = -870876302500090604L;

	public PenaltyTypeId() {
		super();
	}

	public PenaltyTypeId(String identifier) {
		super(identifier);
	}
}
