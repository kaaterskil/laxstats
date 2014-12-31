package laxstats.domain.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laxstats.api.events.AttendeeDTO;
import laxstats.api.events.AttendeeDeletedEvent;
import laxstats.api.events.AttendeeRegisteredEvent;
import laxstats.api.events.AttendeeUpdatedEvent;
import laxstats.api.events.Conditions;
import laxstats.api.events.EventCreatedEvent;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventDeletedEvent;
import laxstats.api.events.EventId;
import laxstats.api.events.EventUpdatedEvent;
import laxstats.api.events.Schedule;
import laxstats.api.events.Status;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.api.sites.SiteAlignment;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
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
	private List<TeamSeasonInfo> teams = new ArrayList<>();

	@EventSourcedMember
	private final Map<String, Attendee> attendees = new HashMap<>();

	public Event(EventId eventId, EventDTO eventDTO) {
		apply(new EventCreatedEvent(eventId, eventDTO));
	}

	protected Event() {
	}

	/* ---------- Methods ---------- */

	public void update(EventId eventId, EventDTO eventDTO) {
		apply(new EventUpdatedEvent(eventId, eventDTO));
	}

	public void delete(EventId eventId) {
		apply(new EventDeletedEvent(eventId));
	}

	/**
	 * Preconditions:
	 * <ol>
	 * <li>Attendees cannot already be registered.</li>
	 * <li>Athletes must not be inactive or injured</li>
	 * </ol>
	 *
	 * @param dto
	 */
	public void registerAttendee(AttendeeDTO dto) {
		if (canRegisterAttendee(dto)) {
			throw new IllegalArgumentException("invalid attendee");
		}
		apply(new AttendeeRegisteredEvent(id, dto));
	}

	public void updateAttendee(AttendeeDTO dto) {
		if (!isAttendeeRegistered(dto.getId())) {
			throw new IllegalArgumentException(
					"attendee is not registered for this event");
		}
		apply(new AttendeeUpdatedEvent(id, dto));
	}

	public void deleteAttendee(String attendeeId) {
		if (!isAttendeeRegistered(attendeeId)) {
			throw new IllegalArgumentException(
					"attendee is not registered for this event");
		}
		apply(new AttendeeDeletedEvent(id, attendeeId));
	}

	private boolean canRegisterAttendee(AttendeeDTO dto) {
		if (!isAttendeeRegistered(dto.getId())) {
			// Athlete must not be inactive or injured
			if (dto.getPlayer() != null && dto.getRole().equals(Role.ATHLETE)) {
				final PlayerStatus status = dto.getPlayer().getStatus();
				return status.equals(PlayerStatus.ACTIVE)
						|| status.equals(PlayerStatus.TRYOUT);
			}
			return true;
		}
		return false;
	}

	private boolean isAttendeeRegistered(String attendeeId) {
		return attendees.containsKey(attendeeId);
	}

	/* ---------- Event handling ---------- */

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

	@EventSourcingHandler
	protected void handle(AttendeeRegisteredEvent event) {
		final AttendeeDTO dto = event.getAttendeeDTO();
		String teamSeasonId = null;
		String playerId = null;
		if (dto.getTeamSeason() != null) {
			teamSeasonId = dto.getTeamSeason().getId();
		}
		if (dto.getPlayer() != null) {
			playerId = dto.getPlayer().getId();
		}

		final Attendee entity = new Attendee(dto.getId(), playerId,
				teamSeasonId, dto.getName(), dto.getJerseyNumber(),
				dto.getRole(), dto.getStatus());
		attendees.put(dto.getId(), entity);
	}

	@EventSourcingHandler
	protected void handle(AttendeeDeletedEvent event) {
		attendees.remove(event.getAttendeeId());
	}

	/* ---------- Getters ---------- */

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

	public Map<String, Attendee> getAttendees() {
		return attendees;
	}
}
