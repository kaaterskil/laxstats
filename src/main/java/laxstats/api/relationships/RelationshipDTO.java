package laxstats.api.relationships;

import laxstats.query.people.PersonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class RelationshipDTO {
	private final RelationshipId relationshipId;
	private final PersonEntry parent;
	private final PersonEntry child;
	private final RelationshipType type;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;

	public RelationshipDTO(RelationshipId relationshipId, PersonEntry parent,
			PersonEntry child, RelationshipType type, LocalDateTime createdAt,
			UserEntry createdBy) {
		super();
		this.relationshipId = relationshipId;
		this.parent = parent;
		this.child = child;
		this.type = type;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
	}

	public RelationshipId getRelationshipId() {
		return relationshipId;
	}

	public PersonEntry getParent() {
		return parent;
	}

	public PersonEntry getChild() {
		return child;
	}

	public RelationshipType getType() {
		return type;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}
}
