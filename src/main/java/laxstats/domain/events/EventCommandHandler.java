package laxstats.domain.events;

import laxstats.api.events.CreateEventCommand;
import laxstats.api.events.DeleteEventCommand;
import laxstats.api.events.EventId;
import laxstats.api.events.UpdateEventCommand;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EventCommandHandler {
	private Repository<Event> repository;

	@Autowired
	@Qualifier("eventRepository")
	public void setRepository(Repository<Event> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public EventId handle(CreateEventCommand command) {
		final EventId eventId = command.getEventId();
		final Event event = new Event(eventId, command.getEventDTO());
		repository.add(event);
		return eventId;
	}

	@CommandHandler
	public void handle(UpdateEventCommand command) {
		final EventId eventId = command.getEventId();
		final Event event = repository.load(eventId);
		event.update(eventId, command.getEventDTO());
	}

	@CommandHandler
	public void handle(DeleteEventCommand command) {
		final EventId eventId = command.getEventId();
		final Event event = repository.load(eventId);
		event.delete(eventId);
	}
}
