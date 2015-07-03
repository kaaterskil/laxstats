package laxstats.domain.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.AttendeeDeleted;
import laxstats.api.games.AttendeeRegistered;
import laxstats.api.games.AttendeeUpdated;
import laxstats.api.games.ClearDeleted;
import laxstats.api.games.ClearRecorded;
import laxstats.api.games.ClearUpdated;
import laxstats.api.games.Conditions;
import laxstats.api.games.DeleteClear;
import laxstats.api.games.DeleteFaceOff;
import laxstats.api.games.DeleteGoal;
import laxstats.api.games.DeleteGroundBall;
import laxstats.api.games.DeletePenalty;
import laxstats.api.games.DeleteShot;
import laxstats.api.games.FaceOffDeleted;
import laxstats.api.games.FaceOffRecorded;
import laxstats.api.games.FaceOffUpdated;
import laxstats.api.games.GameCreated;
import laxstats.api.games.GameDTO;
import laxstats.api.games.GameDeleted;
import laxstats.api.games.GameId;
import laxstats.api.games.GameUpdated;
import laxstats.api.games.GoalDeleted;
import laxstats.api.games.GoalRecorded;
import laxstats.api.games.GoalUpdated;
import laxstats.api.games.GroundBallDeleted;
import laxstats.api.games.GroundBallRecorded;
import laxstats.api.games.GroundBallUpdated;
import laxstats.api.games.InvalidPlayException;
import laxstats.api.games.PenaltyDeleted;
import laxstats.api.games.PenaltyRecorded;
import laxstats.api.games.PenaltyUpdated;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayRole;
import laxstats.api.games.Schedule;
import laxstats.api.games.ShotDeleted;
import laxstats.api.games.ShotRecorded;
import laxstats.api.games.ShotUpdated;
import laxstats.api.games.Status;
import laxstats.api.sites.SiteAlignment;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

/**
 * {@code Game} represents the domain object model of a game.
 */
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

   /**
    * Applies the given aggregate identifier and information to a new game.
    *
    * @param gameId
    * @param gameDTO
    */
   public Game(GameId gameId, GameDTO gameDTO) {
      apply(new GameCreated(gameId, gameDTO));
   }

   /**
    * Creates an empty {@code Game}. Internal use only.
    */
   protected Game() {
   }

   /**
    * Instructs the framework to change the state of this game with the given information.
    *
    * @param gameId
    * @param gameDTO
    */
   public void update(GameId gameId, GameDTO gameDTO) {
      apply(new GameUpdated(gameId, gameDTO));
   }

   /**
    * Instructs the framework to mark this game for deletion.
    *
    * @param gameId
    */
   public void delete(GameId gameId) {
      apply(new GameDeleted(gameId));
   }

   /**
    * Returns a list of game penalties, sorted by elapsed time with respect to the game clock.
    *
    * @return
    */
   public List<Penalty> getPenalties() {
      final List<Penalty> list = new ArrayList<>();
      for (final Play play : plays.values()) {
         if (play instanceof Penalty) {
            list.add((Penalty)play);
         }
      }
      Collections.sort(list, new PenaltyComparator());
      return list;
   }

   /**
    * Returns true if the given attendee is sidelined with a penalty at the specified time, false
    * otherwise.
    *
    * @param attendeeId
    * @param instant
    * @return True if the specified player is sidelined with a penalty at the specified time, false
    *         otherwise.
    */
   public boolean isPlayerSidelined(String attendeeId, LocalDateTime instant) {
      for (final Penalty penalty : getPenalties()) {
         final Interval interval = penalty.getInterval();
         if (interval.contains(instant.toDateTime())) {
            for (final PlayParticipant participant : penalty.getParticipants()) {
               final String participantId = participant.getId();
               final PlayRole role = participant.getRole();
               if (participantId.equals(attendeeId) && role.equals(PlayRole.PENALTY_COMMITTED_BY)) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   /**
    * Instructs the framework to create a new game attendee from the given information.
    *
    * @param dto
    */
   public void registerAttendee(AttendeeDTO dto) {
      apply(new AttendeeRegistered(id, dto));
   }

   /**
    * Instructs the framework to change the state of an existing game attendee with the given
    * information.
    *
    * @param dto
    */
   public void updateAttendee(AttendeeDTO dto) {
      apply(new AttendeeUpdated(id, dto));
   }

   /**
    * Instructs the framework to delete the game attendee matching the given ientifier.
    *
    * @param attendeeId
    */
   public void deleteAttendee(String attendeeId) {
      apply(new AttendeeDeleted(id, attendeeId));
   }

   /**
    * Returns true if the player represented by the given identifier can be registered as a game
    * attendee, false otherwise.
    *
    * @param playerId
    * @return
    */
   public boolean canRegisterAttendee(String playerId) {
      for (final Attendee each : attendees.values()) {
         if (each.getPlayerId().equals(playerId)) {
            return false;
         }
      }
      return true;
   }

   /**
    * Returns true if the plauer represented by the given identifier is registered as a game
    * attendee, false otherwise.
    *
    * @param playerId
    * @return
    */
   public boolean isRegisteredAttendee(String playerId) {
      for (final Attendee each : attendees.values()) {
         if (each.getPlayerId().equals(playerId)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Instructs the framework to create a new clear from the given information. An
    * InvalidPlayException is thrown if the clear cannot be created for ny reason.
    *
    * @param dto
    */
   public void recordClear(PlayDTO dto) {
      final PlayService service = new ClearService(this);
      if (!service.canRecordPlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new ClearRecorded(id, dto.getIdentifier(), dto));
   }

   /**
    * Insructs the framework to change the state of an existing clear with the given information. An
    * InvalidPlayException is thrown if the clear cannot be updated for any reason.
    *
    * @param dto
    */
   public void updateClear(PlayDTO dto) {
      final PlayService service = new ClearService(this);
      if (!service.canUpdatePlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new ClearUpdated(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to delete the clear matching the unique identifier contained in the
    * given event.
    *
    * @param command
    */
   public void deleteClear(DeleteClear command) {
      apply(new ClearDeleted(id, command.getPlayId()));
   }

   /**
    * Instructs the framework to create a new face-off from the given information. An
    * InvalidPlayException is thrown if the face-off cannot be created for any reason.
    *
    * @param dto
    */
   public void recordFaceOff(PlayDTO dto) {
      final PlayService service = new PlayServiceImpl(this);
      if (!service.canRecordPlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new FaceOffRecorded(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to change the state of an existing face-off with the given
    * information. An InvalidPlayException is thrown if the face-off cannot be updated for any
    * reason.
    *
    * @param dto
    */
   public void updateFaceOff(PlayDTO dto) {
      final PlayService service = new PlayServiceImpl(this);
      if (!service.canUpdatePlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new FaceOffUpdated(id, dto.getIdentifier(), dto));
   }

   /**
    * Instrucs he framework to delete the face-off matching the unique identifier contained in the
    * given event.
    *
    * @param command
    */
   public void deleteFaceOff(DeleteFaceOff command) {
      apply(new FaceOffDeleted(id, command.getPlayId()));
   }

   /**
    * Instructs the framework to create a new goal from the given information. An
    * InvalidPlayException is thrown if the goal cannot be created for any reason.
    *
    * @param dto
    */
   public void recordGoal(PlayDTO dto) {
      final PlayService service = new GoalService(this);
      if (!service.canRecordPlay(dto)) {
         throw new InvalidPlayException();
      }
      service.setInvariants(dto);
      apply(new GoalRecorded(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to change the state of an existing goal with the given information. An
    * InvalidPlayException is thrown if the goal cannot be updated for any reason.
    *
    * @param dto
    */
   public void updateGoal(PlayDTO dto) {
      final PlayService service = new GoalService(this);
      if (!service.canUpdatePlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new GoalUpdated(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to delete the goal matching the unique identifier contained in the
    * given event.
    *
    * @param command
    */
   public void deleteGoal(DeleteGoal command) {
      apply(new GoalDeleted(id, command.getPlayId()));
   }

   /**
    * Instructs the framework to create a ground ball from the given information. An
    * InvalidPlayException is thrown if the ground ball cannot be created for any reason.
    *
    * @param dto
    */
   public void recordGroundBall(PlayDTO dto) {
      final PlayService service = new PlayServiceImpl(this);
      if (!service.canRecordPlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new GroundBallRecorded(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to change the state of an existing ground ball with the given
    * information. An InvalidPlayException is thrown if the ground ball cannot be updated for any
    * reason.
    *
    * @param dto
    */
   public void updateGroundBall(PlayDTO dto) {
      final PlayService service = new PlayServiceImpl(this);
      if (!service.canUpdatePlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new GroundBallUpdated(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to delete the ground ball matching the unique identifier contained in
    * the given event.
    *
    * @param command
    */
   public void deleteGroundBall(DeleteGroundBall command) {
      apply(new GroundBallDeleted(id, command.getPlayId()));
   }

   /**
    * Instructs the framework to create a new penalty from the given information. An
    * InvalidPlayException is thrown if the penalty cannot be created for any reason.
    *
    * @param dto
    */
   public void recordPenalty(PlayDTO dto) {
      final PenaltyService service = new PenaltyService(this);
      if (!service.canRecordPlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new PenaltyRecorded(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to change the state of an existing penalty with the given information.
    * An InvalidPlayEception is thrown if the penalty cannot eb updated for any reason.
    *
    * @param dto
    */
   public void updatePenalty(PlayDTO dto) {
      final PenaltyService service = new PenaltyService(this);
      if (!service.canUpdatePlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new PenaltyUpdated(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to delete the penalty matching the unique identifier contained in the
    * given command.
    *
    * @param command
    */
   public void deletePenalty(DeletePenalty command) {
      apply(new PenaltyDeleted(id, command.getPlayId()));
   }

   /**
    * Instructs the framework to create a new shot from the given information. An
    * InvalidPlayException is thrown if the shot cannot be created for any reason.
    *
    * @param dto
    */
   public void recordShot(PlayDTO dto) {
      final PlayService service = new PlayServiceImpl(this);
      if (!service.canRecordPlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new ShotRecorded(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to change the state of an existing shot with the given information. An
    * InvalidPlayException is thrown if the shot cannot be updated for any reason.
    *
    * @param dto
    */
   public void updateShot(PlayDTO dto) {
      final PlayService service = new PlayServiceImpl(this);
      if (!service.canUpdatePlay(dto)) {
         throw new InvalidPlayException();
      }
      apply(new ShotUpdated(id, dto.getIdentifier(), dto));
   }

   /**
    * Instructs the framework to delete the shot matching the unique identifier contained in the
    * given command.
    *
    * @param command
    */
   public void deleteShot(DeleteShot command) {
      apply(new ShotDeleted(id, command.getPlayId()));
   }

   /**
    * Stores and persists the new game state from information contained in the given event,
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GameCreated event) {
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

   /**
    * Updates and persists changes to the game with information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GameUpdated event) {
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

   /**
    * Creates and persists teams from the given information.
    *
    * @param dto
    * @return
    */
   private List<TeamSeasonInfo> createTeams(GameDTO dto) {
      final List<TeamSeasonInfo> teams = new ArrayList<>();
      if (dto.getTeamOne() != null) {
         final TeamSeasonInfo vo =
            new TeamSeasonInfo(dto.getTeamOne().getId(), dto.getTeamOne().getTeam().getName(), dto
               .getTeamOneAlignment());
         teams.add(vo);
      }
      if (dto.getTeamTwo() != null) {
         final TeamSeasonInfo vo =
            new TeamSeasonInfo(dto.getTeamTwo().getId(), dto.getTeamTwo().getTeam().getName(), dto
               .getTeamTwoAlignment());
         teams.add(vo);
      }
      return teams;
   }

   /**
    * Marks this game for deletion.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GameDeleted event) {
      markDeleted();
   }

   /**
    * Creates and persists a game attendee from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(AttendeeRegistered event) {
      final AttendeeDTO dto = event.getAttendeeDTO();
      String teamSeasonId = null;
      String playerId = null;
      if (dto.getTeamSeason() != null) {
         teamSeasonId = dto.getTeamSeason().getId();
      }
      if (dto.getPlayer() != null) {
         playerId = dto.getPlayer().getId();
      }

      final Attendee entity =
         new Attendee(dto.getId(), playerId, teamSeasonId, dto.getName(), dto.getJerseyNumber(), dto
            .getRole(), dto.getStatus());
      attendees.put(dto.getId(), entity);
   }

   /**
    * Deletes the game attendee matching the unique identifier contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(AttendeeDeleted event) {
      attendees.remove(event.getAttendeeId());
   }

   /**
    * Creates and persists a clear from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(ClearRecorded event) {
      final PlayDTO dto = event.getPlayDTO();
      final String eventId = id.toString();
      final String playId = dto.getIdentifier();
      final String teamId = dto.getTeam().getId();

      final Clear entity =
         new Clear(playId, eventId, teamId, dto.getPeriod(), dto.getResult(), dto.getComment());
      plays.put(playId, entity);
   }

   /**
    * Deletes the clear matching the unique identifier contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(ClearDeleted event) {
      plays.remove(event.getPlayId());
   }

   /**
    * Creates and persits a face-off from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(FaceOffRecorded event) {
      final PlayDTO dto = event.getPlayDTO();
      final String eventId = id.toString();
      final String playId = dto.getIdentifier();
      final String teamId = dto.getTeam().getId();

      final FaceOff entity =
         new FaceOff(playId, eventId, teamId, dto.getPeriod(), dto.getComment(), dto
            .getParticipants());
      plays.put(playId, entity);
   }

   /**
    * Deletes the penalty matching the unique identifier contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(FaceOffDeleted event) {
      plays.remove(event.getPlayId());
   }

   /**
    * Creates and persists a goal from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GoalRecorded event) {
      final PlayDTO dto = event.getPlayDTO();
      final String eventId = id.toString();
      final String playId = event.getPlayId();
      final String teamId = dto.getTeam().getId();
      final String manUpTeamId = dto.getManUpTeam() == null ? null : dto.getManUpTeam().getId();

      final Goal entity =
         new Goal(playId, eventId, teamId, dto.getPeriod(), dto.getElapsedTime(), dto
            .getAttemptType(), dto.getComment(), dto.getSequence(), dto.getTeamScore(), dto
            .getOpponentScore(), dto.getStrength(), dto.getManUpAdvantage(), manUpTeamId, dto
            .getParticipants());
      plays.put(playId, entity);
   }

   /**
    * Deletes the goal matching the unique identifier contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GoalDeleted event) {
      plays.remove(event.getPlayId());
   }

   /**
    * Creates and persists a ground ball from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GroundBallRecorded event) {
      final PlayDTO dto = event.getPlayDTO();
      final String eventId = id.toString();
      final String playId = dto.getIdentifier();
      final String teamId = dto.getTeam().getId();

      final GroundBall entity =
         new GroundBall(playId, eventId, teamId, dto.getPeriod(), dto.getComment(), dto
            .getParticipants());
      plays.put(playId, entity);
   }

   /**
    * Deletes the gorund ball matching the unique identifier contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GroundBallDeleted event) {
      plays.remove(event.getPlayId());
   }

   /**
    * Creates and persists a penalty from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PenaltyRecorded event) {
      final PlayDTO dto = event.getPlayDTO();
      final String eventId = id.toString();
      final String playId = dto.getIdentifier();
      final String teamId = dto.getTeam().getId();

      final Penalty entity =
         new Penalty(playId, eventId, teamId, dto.getEvent().getStartsAt(), dto.getPeriod(), dto
            .getElapsedTime(), dto.getViolation().getId(), dto.getPenaltyDuration(), dto
            .getComment(), dto.getParticipants());
      plays.put(playId, entity);
   }

   /**
    * Deletes the penalty matching the unique identifier contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PenaltyDeleted event) {
      plays.remove(event.getPlayId());
   }

   /**
    * Creates and persits a shot from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(ShotRecorded event) {
      final PlayDTO dto = event.getPlayDTO();
      final String eventId = id.toString();
      final String playId = dto.getIdentifier();
      final String teamId = dto.getTeam().getId();

      final Shot entity =
         new Shot(playId, eventId, teamId, dto.getPeriod(), dto.getElapsedTime(), dto
            .getAttemptType(), dto.getResult(), dto.getComment(), dto.getParticipants());
      plays.put(playId, entity);
   }

   /**
    * Deletes the shot matching the unique identifer contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(ShotDeleted event) {
      plays.remove(event.getPlayId());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public GameId getIdentifier() {
      return id;
   }

   /**
    * Returns the identifier of the associated playing field.
    *
    * @return
    */
   public String getSiteId() {
      return siteId;
   }

   /**
    * Returns the game alignment.
    *
    * @return
    */
   public SiteAlignment getAlignment() {
      return alignment;
   }

   /**
    * Returns the date and time of the start of the game.
    *
    * @return
    */
   public LocalDateTime getStartsAt() {
      return startsAt;
   }

   /**
    * Returns the game schedule, e.g. Regular, Pre-Season, etc.
    *
    * @return
    */
   public Schedule getSchedule() {
      return schedule;
   }

   /**
    * Returns the game status.
    *
    * @return
    */
   public Status getStatus() {
      return status;
   }

   /**
    * Returns the weather conditions at the game.
    *
    * @return
    */
   public Conditions getConditions() {
      return conditions;
   }

   /**
    * Returns the game description, or null if none.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Returns an unmodified list of the two opposing teams.
    *
    * @return
    */
   public List<TeamSeasonInfo> getTeams() {
      return Collections.unmodifiableList(teams);
   }

   /**
    * Returns the map of game attendees. Keys are represented by each attendee's unique identifier.
    *
    * @return
    */
   public Map<String, Attendee> getAttendees() {
      return attendees;
   }

   /**
    * Returns the map of plays. Keys are represented by the plays' unique identifier.
    *
    * @return
    */
   public Map<String, Play> getPlays() {
      return plays;
   }

   /**
    * Class object used to sort penalties by elapse time with repsect to the game clock.
    */
   private static class PenaltyComparator implements Comparator<Penalty> {
      @Override
      public int compare(Penalty o1, Penalty o2) {
         final Seconds t1 = o1.getTotalElapsedTime().toStandardSeconds();
         final Seconds t2 = o2.getTotalElapsedTime().toStandardSeconds();
         return t1.getSeconds() < t2.getSeconds() ? -1 : (t1.getSeconds() > t2.getSeconds() ? 1 : 0);
      }

   }
}
