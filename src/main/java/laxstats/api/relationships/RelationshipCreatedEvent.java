package laxstats.api.relationships;

public class RelationshipCreatedEvent {
	private final RelationshipId identifier;
	private final RelationshipDTO relationshipDTO;

	public RelationshipCreatedEvent(RelationshipId identifier,
			RelationshipDTO relationshipDTO) {
		super();
		this.identifier = identifier;
		this.relationshipDTO = relationshipDTO;
	}

	public RelationshipId getIdentifier() {
		return identifier;
	}

	public RelationshipDTO getRelationshipDTO() {
		return relationshipDTO;
	}
}
