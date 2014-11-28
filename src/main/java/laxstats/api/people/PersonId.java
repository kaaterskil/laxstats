package laxstats.api.people;

import laxstats.api.AggregateId;

public class PersonId extends AggregateId {
	private static final long serialVersionUID = 3188225379496683390L;

	public PersonId() {
		super();
	}

	public PersonId(String identifier) {
		super(identifier);
	}
}
