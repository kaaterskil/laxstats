package laxstats.domain.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laxstats.api.events.AttendeeDTO;
import laxstats.api.events.AttendeeDeletedEvent;
import laxstats.api.events.AttendeeNotRegisteredException;
import laxstats.api.events.AttendeeRegisteredEvent;
import laxstats.api.events.AttendeeUpdatedEvent;
import laxstats.api.events.ClearRecordedEvent;
import laxstats.api.events.Conditions;
import laxstats.api.events.EventCreatedEvent;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventDeletedEvent;
import laxstats.api.events.EventId;
import laxstats.api.events.EventUpdatedEvent;
import laxstats.api.events.GoalRecordedEvent;
import laxstats.api.events.GroundBallRecordedEvent;
import laxstats.api.events.InvalidPlayException;
import laxstats.api.events.PlayDTO;
import laxstats.api.events.Schedule;
import laxstats.api.events.ShotRecordedEvent;
import laxstats.api.events.Status;
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

	@EventSourcedMember
	private final Map<String, Play> plays = new HashMap<>();

	/*---------- Constructors ----------*/

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

	/* ---------- Attendee methods ---------- */

	public void registerAttendee(AttendeeDTO dto) {
		final AttendeeValidator validator = new AttendeeValidator(this);
		if (!validator.canRegisterAttendee(dto)) {
			throw new IllegalArgumentException("invalid attendee");
		}
		apply(new AttendeeRegisteredEvent(id, dto));
	}

	public void updateAttendee(AttendeeDTO dto)
			throws AttendeeNotRegisteredException {
		final AttendeeValidator validator = new AttendeeValidator(this);
		if (!validator.isAttendeeRegistered(dto.getId())) {
			final String msg = dto.getName()
					+ " is not registered for this event";
			throw new AttendeeNotRegisteredException(msg);
		}
		apply(new AttendeeUpdatedEvent(id, dto));
	}

	public void deleteAttendee(String attendeeId)
			throws AttendeeNotRegisteredException {
		final AttendeeValidator validator = new AttendeeValidator(this);
		if (!validator.isAttendeeRegistered(attendeeId)) {
			throw new AttendeeNotRegisteredException();
		}
		apply(new AttendeeDeletedEvent(id, attendeeId));
	}

	/*---------- Play methods ----------*/

	public void recordGoal(PlayDTO dto) {
		final PlayService service = new GoalService(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		service.setInvariants(dto);
		apply(new GoalRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void recordShot(PlayDTO dto) {
		final PlayService service = new PlayServiceImpl(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new ShotRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void recordGroundBall(PlayDTO dto) {
		final PlayService service = new PlayServiceImpl(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new GroundBallRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void recordClear(PlayDTO dto) {
		final PlayService service = new ClearService(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new ClearRecordedEvent(id, dto.getIdentifier(), dto));
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

	@EventSourcingHandler
	protected void handle(GoalRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = event.getPlayId().toString();
		final String teamId = dto.getTeam().getId();
		final String manUpTeamId = dto.getManUpTeam() == null ? null : dto
				.getManUpTeam().getId();

		final Goal entity = new Goal(playId, eventId, teamId, dto.getPeriod(),
				dto.getElapsedTime(), dto.getAttemptType(), dto.getComment(),
				dto.getSequence(), dto.getTeamScore(), dto.getOpponentScore(),
				dto.getStrength(), dto.getManUpAdvantage(), manUpTeamId,
				dto.getParticipants());
		plays.put(playId, entity);
	}

	@EventSourcingHandler
	protected void handle(ShotRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = dto.getIdentifier().toString();
		final String teamId = dto.getTeam().getId();

		final Shot entity = new Shot(playId, eventId, teamId, dto.getPeriod(),
				dto.getElapsedTime(), dto.getAttemptType(), dto.getResult(),
				dto.getComment(), dto.getParticipants());
		plays.put(playId, entity);
	}

	@EventSourcingHandler
	protected void handle(GroundBallRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = dto.getIdentifier().toString();
		final String teamId = dto.getTeam().getId();

		final GroundBall entity = new GroundBall(playId, eventId, teamId,
				dto.getPeriod(), dto.getComment(), dto.getParticipants());
		plays.put(playId, entity);
	}

	@EventSourcingHandler
	protected void handle(ClearRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = dto.getIdentifier().toString();
		final String teamId = dto.getTeam().getId();

		final Clear entity = new Clear(playId, eventId, teamId,
				dto.getPeriod(), dto.getResult(), dto.getComment());
		plays.put(playId, entity);
	}

	/* ---------- Getters ---------- */

	@Override
	public EventId getIdentifier() {
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

	public Map<String, Play> getPlays() {
		return plays;
	}
}
