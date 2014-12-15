package laxstats.query.events;

import laxstats.api.events.EventCreatedEvent;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventDeletedEvent;
import laxstats.api.events.EventId;
import laxstats.api.events.EventUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListener {
	private EventQueryRepository repository;

	@Autowired
	public void setRepository(EventQueryRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	protected void handle(EventCreatedEvent event) {
		final EventId eventId = event.getEventId();
		final EventDTO dto = event.getEventDTO();

		final EventEntry entity = new EventEntry();
		entity.setId(eventId.toString());
		entity.setSite(dto.getSite());
		entity.setAlignment(dto.getAlignment());
		entity.setStartsAt(dto.getStartsAt());
		entity.setSchedule(dto.getSchedule());
		entity.setStatus(dto.getStatus());
		entity.setConditions(dto.getConditions());
		entity.setDescription(dto.getDescription());
		entity.setCreatedAt(dto.getCreatedAt());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setModifiedAt(dto.getModifiedAt());
		entity.setModifiedBy(dto.getModifiedBy());
		repository.save(entity);
	}

	@EventHandler
	protected void handle(EventUpdatedEvent event) {
		final EventId eventId = event.getEventId();
		final EventDTO dto = event.getEventDTO();

		final EventEntry entity = repository.findOne(eventId.toString());
		entity.setSite(dto.getSite());
		entity.setAlignment(dto.getAlignment());
		entity.setStartsAt(dto.getStartsAt());
		entity.setSchedule(dto.getSchedule());
		entity.setStatus(dto.getStatus());
		entity.setConditions(dto.getConditions());
		entity.setDescription(dto.getDescription());
		entity.setModifiedAt(dto.getModifiedAt());
		entity.setModifiedBy(dto.getModifiedBy());
		repository.save(entity);
	}

	@EventHandler
	protected void handle(EventDeletedEvent event) {
		final EventId eventId = event.getEventId();
		final EventEntry entity = repository.findOne(eventId.toString());
		repository.delete(entity);
	}
}
