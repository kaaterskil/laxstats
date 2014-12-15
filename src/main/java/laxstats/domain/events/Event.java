package laxstats.domain.events;

import java.util.HashSet;
import java.util.Set;

import laxstats.api.events.Alignment;
import laxstats.api.events.Conditions;
import laxstats.api.events.EventCreatedEvent;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventDeletedEvent;
import laxstats.api.events.EventId;
import laxstats.api.events.EventUpdatedEvent;
import laxstats.api.events.Schedule;
import laxstats.api.events.Status;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDateTime;

public class Event extends AbstractAnnotatedAggregateRoot<EventId> {
	private static final long serialVersionUID = 2833813418469491250L;

	@AggregateIdentifier
	private EventId id;
	private String siteId;
	private Alignment alignment;
	private LocalDateTime startsAt;
	private Schedule schedule;
	private Status status;
	private Conditions conditions;
	private String description;
	private final Set<EventAttendee> eventAttendees = new HashSet<>();
	private final Set<TeamEvent> eventTeams = new HashSet<>();

	public Event(EventId eventId, EventDTO eventDTO) {
		apply(new EventCreatedEvent(eventId, eventDTO));
	}

	// ---------- Methods ----------//

	public void update(EventId eventId, EventDTO eventDTO) {
		apply(new EventUpdatedEvent(eventId, eventDTO));
	}

	public void delete(EventId eventId) {
		apply(new EventDeletedEvent(eventId));
	}

	// ---------- Event handling ----------//

	@EventSourcingHandler
	protected void handle(EventCreatedEvent event) {
		final EventDTO dto = event.getEventDTO();

		id = event.getEventId();
		siteId = dto.getSite().toString();
		alignment = dto.getAlignment();
		startsAt = dto.getStartsAt();
		schedule = dto.getSchedule();
		status = dto.getStatus();
		conditions = dto.getConditions();
		description = dto.getDescription();
	}

	@EventSourcingHandler
	protected void handle(EventUpdatedEvent event) {
		final EventDTO dto = event.getEventDTO();

		siteId = dto.getSite().toString();
		alignment = dto.getAlignment();
		startsAt = dto.getStartsAt();
		schedule = dto.getSchedule();
		status = dto.getStatus();
		conditions = dto.getConditions();
		description = dto.getDescription();
	}

	@EventSourcingHandler
	protected void handle(EventDeletedEvent event) {
		markDeleted();
	}

	// ---------- Getters ----------//

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public EventId getId() {
		return id;
	}

	public String getSiteId() {
		return siteId;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public Status getStatus() {
		return status;
	}

	public Conditions getConditions() {
		return conditions;
	}

	public String getDescription() {
		return description;
	}

	public Set<EventAttendee> getEventAttendees() {
		return eventAttendees;
	}

	public Set<TeamEvent> getEventTeams() {
		return eventTeams;
	}
}
