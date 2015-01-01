package laxstats.api.relationships;

public class CreateRelationshipCommand extends AbstractRelationshipCommand {
	private final RelationshipDTO relationshipDTO;

	public CreateRelationshipCommand(RelationshipId identifier,
			RelationshipDTO relationshipDTO) {
		super(identifier);
		this.relationshipDTO = relationshipDTO;
	}

	public RelationshipDTO getRelationshipDTO() {
		return relationshipDTO;
	}
}
