package laxstats.domain.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.AttendeeDeletedEvent;
import laxstats.api.games.AttendeeRegisteredEvent;
import laxstats.api.games.AttendeeUpdatedEvent;
import laxstats.api.games.ClearDeletedEvent;
import laxstats.api.games.ClearRecordedEvent;
import laxstats.api.games.ClearUpdatedEvent;
import laxstats.api.games.Conditions;
import laxstats.api.games.DeleteClearCommand;
import laxstats.api.games.DeleteFaceOffCommand;
import laxstats.api.games.DeleteGoalCommand;
import laxstats.api.games.DeleteGroundBallCommand;
import laxstats.api.games.DeletePenaltyCommand;
import laxstats.api.games.DeleteShotCommand;
import laxstats.api.games.FaceOffDeletedEvent;
import laxstats.api.games.FaceOffRecordedEvent;
import laxstats.api.games.FaceOffUpdatedEvent;
import laxstats.api.games.GameCreatedEvent;
import laxstats.api.games.GameDTO;
import laxstats.api.games.GameDeletedEvent;
import laxstats.api.games.GameId;
import laxstats.api.games.GameUpdatedEvent;
import laxstats.api.games.GoalDeletedEvent;
import laxstats.api.games.GoalRecordedEvent;
import laxstats.api.games.GoalUpdatedEvent;
import laxstats.api.games.GroundBallDeletedEvent;
import laxstats.api.games.GroundBallRecordedEvent;
import laxstats.api.games.GroundBallUpdatedEvent;
import laxstats.api.games.InvalidPlayException;
import laxstats.api.games.PenaltyDeletedEvent;
import laxstats.api.games.PenaltyRecordedEvent;
import laxstats.api.games.PenaltyUpdatedEvent;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayRole;
import laxstats.api.games.Schedule;
import laxstats.api.games.ShotDeletedEvent;
import laxstats.api.games.ShotRecordedEvent;
import laxstats.api.games.ShotUpdatedEvent;
import laxstats.api.games.Status;
import laxstats.api.sites.SiteAlignment;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

public class Game extends AbstractAnnotatedAggregateRoot<GameId> {
	private static final long serialVersionUID = 2833813418469491250L;

	@AggregateIdentifier
	private GameId id;
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

	public Game(GameId gameId, GameDTO gameDTO) {
		apply(new GameCreatedEvent(gameId, gameDTO));
	}

	protected Game() {
	}

	/* ---------- Methods ---------- */

	public void update(GameId gameId, GameDTO gameDTO) {
		apply(new GameUpdatedEvent(gameId, gameDTO));
	}

	public void delete(GameId gameId) {
		apply(new GameDeletedEvent(gameId));
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
	protected void handle(GameCreatedEvent event) {
		final GameDTO dto = event.getEventDTO();
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
	protected void handle(GameUpdatedEvent event) {
		final GameDTO dto = event.getEventDTO();
		siteId = dto.getSite() == null ? null : dto.getSite().getId();
		alignment = dto.getAlignment();
		startsAt = dto.getStartsAt();
		schedule = dto.getSchedule();
		status = dto.getStatus();
		conditions = dto.getConditions();
		description = dto.getDescription();
		teams = createTeams(dto);
	}

	private List<TeamSeasonInfo> createTeams(GameDTO dto) {
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
	protected void handle(GameDeletedEvent event) {
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
	public GameId getIdentifier() {
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
