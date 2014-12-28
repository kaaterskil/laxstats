package laxstats.domain.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import laxstats.api.events.Conditions;
import laxstats.api.events.EventCreatedEvent;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventDeletedEvent;
import laxstats.api.events.EventId;
import laxstats.api.events.EventUpdatedEvent;
import laxstats.api.events.Schedule;
import laxstats.api.events.Status;
import laxstats.api.sites.SiteAlignment;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDateTime;

public class Event extends AbstractAnnotatedAggregateRoot<EventId> {
	private static final long serialVersionUID = 2833813418469491250L;

	@AggregateIdentifier
	private EventId id;
	private String siteId;
	private SiteAlignment alignment;
	private LocalDateTime startsAt;
	private Schedule schedule;
	private Status status;
	private Conditions conditions;
	private String description;
	private List<TeamSeasonInfo> teams;

	public Event(EventId eventId, EventDTO eventDTO) {
		apply(new EventCreatedEvent(eventId, eventDTO));
	}

	protected Event() {
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
		siteId = dto.getSite() == null ? null : dto.getSite().getId();
		alignment = dto.getAlignment();
		startsAt = dto.getStartsAt();
		schedule = dto.getSchedule();
		status = dto.getStatus();
		conditions = dto.getConditions();
		description = dto.getDescription();
		teams = createTeams(dto);
	}

	@EventSourcingHandler
	protected void handle(EventUpdatedEvent event) {
		final EventDTO dto = event.getEventDTO();

		siteId = dto.getSite() == null ? null : dto.getSite().getId();
		alignment = dto.getAlignment();
		startsAt = dto.getStartsAt();
		schedule = dto.getSchedule();
		status = dto.getStatus();
		conditions = dto.getConditions();
		description = dto.getDescription();
		teams = createTeams(dto);
	}

	private List<TeamSeasonInfo> createTeams(EventDTO dto) {
		final List<TeamSeasonInfo> teams = new ArrayList<>();
		if (dto.getTeamOne() != null) {
			final TeamSeasonInfo vo = new TeamSeasonInfo(dto.getTeamOne()
					.getId(), dto.getTeamOne().getTeam().getName(),
					dto.getTeamOneAlignment());
			teams.add(vo);
		}
		if (dto.getTeamTwo() != null) {
			final TeamSeasonInfo vo = new TeamSeasonInfo(dto.getTeamTwo()
					.getId(), dto.getTeamTwo().getTeam().getName(),
					dto.getTeamTwoAlignment());
			teams.add(vo);
		}
		return teams;
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

	public SiteAlignment getAlignment() {
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

	public List<TeamSeasonInfo> getTeams() {
		return Collections.unmodifiableList(teams);
	}
}
