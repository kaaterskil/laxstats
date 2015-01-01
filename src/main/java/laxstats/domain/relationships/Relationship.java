package laxstats.domain.relationships;

import laxstats.api.relationships.RelationshipCreatedEvent;
import laxstats.api.relationships.RelationshipDTO;
import laxstats.api.relationships.RelationshipDeletedEvent;
import laxstats.api.relationships.RelationshipId;
import laxstats.api.relationships.RelationshipType;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Relationship extends
		AbstractAnnotatedAggregateRoot<RelationshipId> {
	private static final long serialVersionUID = -5458257684506020218L;

	@AggregateIdentifier
	private RelationshipId identifier;
	private String parentId;
	private String parentName;
	private String childId;
	private String childName;
	private RelationshipType type;

	/*---------- Constructors ----------*/

	public Relationship(RelationshipId identifier, RelationshipDTO dto) {
		apply(new RelationshipCreatedEvent(identifier, dto));
	}

	protected Relationship() {
	}

	/*---------- Methods ----------*/

	public void delete() {
		apply(new RelationshipDeletedEvent(identifier));
	}

	/*---------- Event handlers ----------*/

	@EventSourcingHandler
	protected void handle(RelationshipCreatedEvent event) {
		final RelationshipDTO dto = event.getRelationshipDTO();
		identifier = event.getIdentifier();
		parentId = dto.getParent().getId();
		parentName = dto.getParent().getFullName();
		childId = dto.getChild().getId();
		childName = dto.getChild().getFullName();
		type = dto.getType();
	}

	@EventSourcingHandler
	protected void handle(RelationshipDeletedEvent event) {
		markDeleted();
	}

	/*---------- Getters ----------*/

	@Override
	public RelationshipId getIdentifier() {
		return identifier;
	}

	public String getParentId() {
		return parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public String getChildId() {
		return childId;
	}

	public String getChildName() {
		return childName;
	}

	public RelationshipType getType() {
		return type;
	}
}
