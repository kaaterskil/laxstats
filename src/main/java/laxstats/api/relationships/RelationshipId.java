package laxstats.api.relationships;

import laxstats.api.AggregateId;

public class RelationshipId extends AggregateId {
	private static final long serialVersionUID = -2725546764747515160L;

	public RelationshipId() {
		super();
	}

	public RelationshipId(String identifier) {
		super(identifier);
	}
}
