package laxstats.api.users;

import laxstats.api.AggregateId;

public class UserId extends AggregateId {
	private static final long serialVersionUID = -3348074765836159716L;

	public UserId() {
		super();
	}

	public UserId(String identifier) {
		super(identifier);
	}
}
