package laxstats.api.relationships;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class AbstractRelationshipCommand {
	@TargetAggregateIdentifier
	private final RelationshipId identifier;

	protected AbstractRelationshipCommand(RelationshipId identifier) {
		super();
		this.identifier = identifier;
	}

	public RelationshipId getIdentifier() {
		return identifier;
	}
}
