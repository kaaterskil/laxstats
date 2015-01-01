package laxstats.api.relationships;

public class RelationshipDeletedEvent {
	private final RelationshipId identifier;

	public RelationshipDeletedEvent(RelationshipId identifier) {
		this.identifier = identifier;
	}

	public RelationshipId getIdentifier() {
		return identifier;
	}
}
