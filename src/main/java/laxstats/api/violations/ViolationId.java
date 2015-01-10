package laxstats.api.violations;

import laxstats.api.AggregateId;

public class ViolationId extends AggregateId {
	private static final long serialVersionUID = -870876302500090604L;

	public ViolationId() {
		super();
	}

	public ViolationId(String identifier) {
		super(identifier);
	}
}
