package laxstats.domain.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laxstats.api.events.AttendeeDTO;
import laxstats.api.events.AttendeeDeletedEvent;
import laxstats.api.events.AttendeeRegisteredEvent;
import laxstats.api.events.AttendeeUpdatedEvent;
import laxstats.api.events.ClearDeletedEvent;
import laxstats.api.events.ClearRecordedEvent;
import laxstats.api.events.ClearUpdatedEvent;
import laxstats.api.events.Conditions;
import laxstats.api.events.DeleteClearCommand;
import laxstats.api.events.DeleteFaceOffCommand;
import laxstats.api.events.DeleteGoalCommand;
import laxstats.api.events.DeleteGroundBallCommand;
import laxstats.api.events.DeletePenaltyCommand;
import laxstats.api.events.DeleteShotCommand;
import laxstats.api.events.EventCreatedEvent;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventDeletedEvent;
import laxstats.api.events.EventId;
import laxstats.api.events.EventUpdatedEvent;
import laxstats.api.events.FaceOffDeletedEvent;
import laxstats.api.events.FaceOffRecordedEvent;
import laxstats.api.events.FaceOffUpdatedEvent;
import laxstats.api.events.GoalDeletedEvent;
import laxstats.api.events.GoalRecordedEvent;
import laxstats.api.events.GoalUpdatedEvent;
import laxstats.api.events.GroundBallDeletedEvent;
import laxstats.api.events.GroundBallRecordedEvent;
import laxstats.api.events.GroundBallUpdatedEvent;
import laxstats.api.events.InvalidPlayException;
import laxstats.api.events.PenaltyDeletedEvent;
import laxstats.api.events.PenaltyRecordedEvent;
import laxstats.api.events.PenaltyUpdatedEvent;
import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayRole;
import laxstats.api.events.Schedule;
import laxstats.api.events.ShotDeletedEvent;
import laxstats.api.events.ShotRecordedEvent;
import laxstats.api.events.ShotUpdatedEvent;
import laxstats.api.events.Status;
import laxstats.api.sites.SiteAlignment;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

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
	Map<String, Attendee> attendees = new HashMap<>();

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

	public List<Penalty> getPenalties() {
		final List<Penalty> list = new ArrayList<>();
		for (final Play play : plays.values()) {
			if (play instanceof Penalty) {
				list.add((Penalty) play);
			}
		}
		Collections.sort(list, new PenaltyComparator());
		return list;
	}

	/**
	 * Returns true if the given attendee is sidelined with a penalty at the
	 * specified time, false otherwise.
	 *
	 * @param attendeeId
	 * @param instant
	 * @return True if the specified player is sidelined with a penalty at the
	 *         specified time, false otherwise.
	 */
	public boolean isPlayerSidelined(String attendeeId, LocalDateTime instant) {
		for (final Penalty penalty : getPenalties()) {
			final Interval interval = penalty.getInterval();
			if (interval.contains(instant.toDateTime())) {
				for (final PlayParticipant participant : penalty
						.getParticipants()) {
					final String participantId = participant.getId();
					final PlayRole role = participant.getRole();
					if (participantId.equals(attendeeId)
							&& role.equals(PlayRole.PENALTY_COMMITTED_BY)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/* ---------- Attendee methods ---------- */

	public void registerAttendee(AttendeeDTO dto) {
		apply(new AttendeeRegisteredEvent(id, dto));
	}

	public void updateAttendee(AttendeeDTO dto) {
		apply(new AttendeeUpdatedEvent(id, dto));
	}

	public void deleteAttendee(String attendeeId) {
		apply(new AttendeeDeletedEvent(id, attendeeId));
	}

	public boolean canRegisterAttendee(String playerId) {
		for (final Attendee each : attendees.values()) {
			if (each.getPlayerId().equals(playerId)) {
				return false;
			}
		}
		return true;
	}

	public boolean isRegisteredAttendee(String playerId) {
		for (final Attendee each : attendees.values()) {
			if (each.getPlayerId().equals(playerId)) {
				return true;
			}
		}
		return false;
	}

	/*---------- Clear methods ----------*/

	public void recordClear(PlayDTO dto) {
		final PlayService service = new ClearService(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new ClearRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void updateClear(PlayDTO dto) {
		final PlayService service = new ClearService(this);
		if (!service.canUpdatePlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new ClearUpdatedEvent(id, dto.getIdentifier(), dto));
	}

	public void deleteClear(DeleteClearCommand command) {
		apply(new ClearDeletedEvent(id, command.getPlayId()));
	}

	/*---------- FaceOff methods ----------*/

	public void recordFaceOff(PlayDTO dto) {
		final PlayService service = new PlayServiceImpl(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new FaceOffRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void updateFaceOff(PlayDTO dto) {
		final PlayService service = new PlayServiceImpl(this);
		if (!service.canUpdatePlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new FaceOffUpdatedEvent(id, dto.getIdentifier(), dto));
	}

	public void deleteFaceOff(DeleteFaceOffCommand command) {
		apply(new FaceOffDeletedEvent(id, command.getPlayId()));
	}

	/*---------- Goal methods ----------*/

	public void recordGoal(PlayDTO dto) {
		final PlayService service = new GoalService(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		service.setInvariants(dto);
		apply(new GoalRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void updateGoal(PlayDTO dto) {
		final PlayService service = new GoalService(this);
		if (!service.canUpdatePlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new GoalUpdatedEvent(id, dto.getIdentifier(), dto));
	}

	public void deleteGoal(DeleteGoalCommand command) {
		apply(new GoalDeletedEvent(id, command.getPlayId()));
	}

	/*---------- Ground Ball methods ----------*/

	public void recordGroundBall(PlayDTO dto) {
		final PlayService service = new PlayServiceImpl(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new GroundBallRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void updateGroundBall(PlayDTO dto) {
		final PlayService service = new PlayServiceImpl(this);
		if (!service.canUpdatePlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new GroundBallUpdatedEvent(id, dto.getIdentifier(), dto));
	}

	public void deleteGroundBall(DeleteGroundBallCommand command) {
		apply(new GroundBallDeletedEvent(id, command.getPlayId()));
	}

	/*---------- Penalty methods ----------*/

	public void recordPenalty(PlayDTO dto) {
		final PenaltyService service = new PenaltyService(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new PenaltyRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void updatePenalty(PlayDTO dto) {
		final PenaltyService service = new PenaltyService(this);
		if (!service.canUpdatePlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new PenaltyUpdatedEvent(id, dto.getIdentifier(), dto));
	}

	public void deletePenalty(DeletePenaltyCommand command) {
		apply(new PenaltyDeletedEvent(id, command.getPlayId()));
	}

	/*---------- Shot methods ----------*/

	public void recordShot(PlayDTO dto) {
		final PlayService service = new PlayServiceImpl(this);
		if (!service.canRecordPlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new ShotRecordedEvent(id, dto.getIdentifier(), dto));
	}

	public void updateShot(PlayDTO dto) {
		final PlayService service = new PlayServiceImpl(this);
		if (!service.canUpdatePlay(dto)) {
			throw new InvalidPlayException();
		}
		apply(new ShotUpdatedEvent(id, dto.getIdentifier(), dto));
	}

	public void deleteShot(DeleteShotCommand command) {
		apply(new ShotDeletedEvent(id, command.getPlayId()));
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
	protected void handle(ClearRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = dto.getIdentifier();
		final String teamId = dto.getTeam().getId();

		final Clear entity = new Clear(playId, eventId, teamId,
				dto.getPeriod(), dto.getResult(), dto.getComment());
		plays.put(playId, entity);
	}

	@EventSourcingHandler
	protected void handle(ClearDeletedEvent event) {
		plays.remove(event.getPlayId());
	}

	@EventSourcingHandler
	protected void handle(FaceOffRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = dto.getIdentifier();
		final String teamId = dto.getTeam().getId();

		final FaceOff entity = new FaceOff(playId, eventId, teamId,
				dto.getPeriod(), dto.getComment(), dto.getParticipants());
		plays.put(playId, entity);
	}

	@EventSourcingHandler
	protected void handle(FaceOffDeletedEvent event) {
		plays.remove(event.getPlayId());
	}

	@EventSourcingHandler
	protected void handle(GoalRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = event.getPlayId();
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
	protected void handle(GoalDeletedEvent event) {
		plays.remove(event.getPlayId());
	}

	@EventSourcingHandler
	protected void handle(GroundBallRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = dto.getIdentifier();
		final String teamId = dto.getTeam().getId();

		final GroundBall entity = new GroundBall(playId, eventId, teamId,
				dto.getPeriod(), dto.getComment(), dto.getParticipants());
		plays.put(playId, entity);
	}

	@EventSourcingHandler
	protected void handle(GroundBallDeletedEvent event) {
		plays.remove(event.getPlayId());
	}

	@EventSourcingHandler
	protected void handle(PenaltyRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = dto.getIdentifier();
		final String teamId = dto.getTeam().getId();

		final Penalty entity = new Penalty(playId, eventId, teamId, dto
				.getEvent().getStartsAt(), dto.getPeriod(),
				dto.getElapsedTime(), dto.getViolation().getId(),
				dto.getPenaltyDuration(), dto.getComment(),
				dto.getParticipants());
		plays.put(playId, entity);
	}

	@EventSourcingHandler
	protected void handle(PenaltyDeletedEvent event) {
		plays.remove(event.getPlayId());
	}

	@EventSourcingHandler
	protected void handle(ShotRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();
		final String eventId = id.toString();
		final String playId = dto.getIdentifier();
		final String teamId = dto.getTeam().getId();

		final Shot entity = new Shot(playId, eventId, teamId, dto.getPeriod(),
				dto.getElapsedTime(), dto.getAttemptType(), dto.getResult(),
				dto.getComment(), dto.getParticipants());
		plays.put(playId, entity);
	}

	@EventSourcingHandler
	protected void handle(ShotDeletedEvent event) {
		plays.remove(event.getPlayId());
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

	private static class PenaltyComparator implements Comparator<Penalty> {
		@Override
		public int compare(Penalty o1, Penalty o2) {
			final Seconds t1 = o1.getTotalElapsedTime().toStandardSeconds();
			final Seconds t2 = o2.getTotalElapsedTime().toStandardSeconds();
			return t1.getSeconds() < t2.getSeconds() ? -1
					: (t1.getSeconds() > t2.getSeconds() ? 1 : 0);
		}

	}
}
