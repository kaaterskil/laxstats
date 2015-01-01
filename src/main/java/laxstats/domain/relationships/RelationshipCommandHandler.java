package laxstats.domain.relationships;

import laxstats.api.relationships.CreateRelationshipCommand;
import laxstats.api.relationships.DeleteRelationshipCommand;
import laxstats.api.relationships.RelationshipDTO;
import laxstats.api.relationships.RelationshipId;
import laxstats.api.relationships.RelationshipType;
import laxstats.query.people.PersonEntry;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RelationshipCommandHandler {
	private Repository<Relationship> repository;

	@Autowired
	@Qualifier(value = "relationshipRepository")
	public void setRepository(Repository<Relationship> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public RelationshipId handle(CreateRelationshipCommand command) {
		final RelationshipId identifier = command.getIdentifier();
		final RelationshipDTO dto = command.getRelationshipDTO();

		if (!canCreate(dto.getParent(), dto.getChild(), dto.getType())) {
			throw new IllegalArgumentException("invalid relationship");
		}

		final Relationship aggregate = new Relationship(identifier, dto);
		repository.add(aggregate);
		return identifier;
	}

	@CommandHandler
	public void handle(DeleteRelationshipCommand command) {
		final RelationshipId identifier = command.getIdentifier();
		final Relationship aggregate = repository.load(identifier);
		aggregate.delete();
	}

	private boolean canCreate(PersonEntry parent, PersonEntry child,
			RelationshipType type) {
		if (parent.equals(child) || parent.ancestorsInclude(child, type)) {
			return false;
		}
		return true;
	}

}
