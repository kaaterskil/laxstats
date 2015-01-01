package laxstats.query.relationships;

import laxstats.api.relationships.RelationshipCreatedEvent;
import laxstats.api.relationships.RelationshipDTO;
import laxstats.api.relationships.RelationshipDeletedEvent;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelationshipListener {
	private RelationshipQueryRepository repository;
	private PersonQueryRepository personRepository;

	@Autowired
	public void setRepository(RelationshipQueryRepository repository) {
		this.repository = repository;
	}

	@Autowired
	public void setPersonRepository(PersonQueryRepository personRepository) {
		this.personRepository = personRepository;
	}

	@EventHandler
	protected void handle(RelationshipCreatedEvent event) {
		final String relationshipId = event.getIdentifier().toString();
		final RelationshipDTO dto = event.getRelationshipDTO();

		final PersonEntry parent = dto.getParent();
		final PersonEntry child = dto.getChild();

		final RelationshipEntry entity = new RelationshipEntry(relationshipId,
				parent, child, dto.getType());

		repository.save(entity);
		personRepository.save(parent);
		personRepository.save(child);
	}

	@EventHandler
	protected void handle(RelationshipDeletedEvent event) {
		final RelationshipEntry entity = repository.findOne(event
				.getIdentifier().toString());
		repository.delete(entity);
	}
}
