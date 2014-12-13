package laxstats.api.sites;

import laxstats.api.AggregateId;

public class SiteId extends AggregateId {
	private static final long serialVersionUID = 5119138108815863082L;

	public SiteId() {
		super();
	}

	public SiteId(String identifier) {
		super(identifier);
	}
}
