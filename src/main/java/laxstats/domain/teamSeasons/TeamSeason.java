package laxstats.domain.teamSeasons;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.games.GameDTO;
import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerId;
import laxstats.api.teamSeasons.DeleteTeamSeason;
import laxstats.api.teamSeasons.EventAlreadyScheduledException;
import laxstats.api.teamSeasons.EventScheduleConflictException;
import laxstats.api.teamSeasons.GameRevised;
import laxstats.api.teamSeasons.GameScheduled;
import laxstats.api.teamSeasons.PlayerDropped;
import laxstats.api.teamSeasons.PlayerEdited;
import laxstats.api.teamSeasons.PlayerRegistered;
import laxstats.api.teamSeasons.TeamSeasonCreated;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonDeleted;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamSeasonUpdated;
import laxstats.api.teamSeasons.TeamStatus;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDate;

/**
 * {@code TeamSeason} represents the domain object model of a season, including the roster, of a
 * team.
 */
public class TeamSeason extends AbstractAnnotatedAggregateRoot<TeamSeasonId> {
   private static final long serialVersionUID = 687219884714746674L;

   @AggregateIdentifier
   private TeamSeasonId id;

   private String teamId;
   private String seasonId;
   private TeamStatus status;
   private LocalDate startsOn;
   private LocalDate endsOn;
   private final List<PlayerInfo> roster = new ArrayList<>();
   private final List<GameInfo> events = new ArrayList<>();

   /**
    * Applies the given aggregate identifier and information to a newly created team season.
    * 
    * @param teamSeasonId
    * @param teamSeasonDTO
    */
   public TeamSeason(TeamSeasonId teamSeasonId, TeamSeasonDTO teamSeasonDTO) {
      apply(new TeamSeasonCreated(teamSeasonId, teamSeasonDTO));
   }

   /**
    * Creates a {@code TeamSeason}. Internal user only.
    */
   protected TeamSeason() {
   }

   /**
    * Instructs the framework to update and persist the state of this team season.
    * 
    * @param identifier
    * @param dto
    */
   public void update(TeamSeasonId identifier, TeamSeasonDTO dto) {
      apply(new TeamSeasonUpdated(identifier, dto));
   }

   /**
    * Instructs the framework to mark this team season for deletion.
    * 
    * @param command
    */
   public void delete(DeleteTeamSeason command) {
      apply(new TeamSeasonDeleted(command.getTeamSeasonId()));
   }

   /**
    * Instructs the framework to add the player with the given information to this team season
    * roster.
    * 
    * @param dto
    */
   public void registerPlayer(PlayerDTO dto) {
      apply(new PlayerRegistered(id, dto));
   }

   /**
    * Returns true if the player matching the given identifier can be added to this team season
    * roster.
    * 
    * @param playerId
    * @return
    */
   public boolean canRegisterPlayer(String playerId) {
      return !isPlayerRegistered(playerId);
   }

   /**
    * Instructs the framework to update and persist the state of a player with the given
    * information.
    * 
    * @param dto
    */
   public void updatePlayer(PlayerDTO dto) {
      apply(new PlayerEdited(id, dto));
   }

   /**
    * Instructs the framework to drop the player matching the given aggregate identifier from this
    * team season roster.
    * 
    * @param playerId
    */
   public void dropPlayer(PlayerId playerId) {
      if (!isPlayerRegistered(playerId.toString())) {
         throw new IllegalArgumentException("player is not registered");
      }
      apply(new PlayerDropped(id, playerId));
   }

   /**
    * Returns true if the player matching the given aggregate identifier is registered on this team
    * season roster, false otherwise.
    * 
    * @param playerId
    * @return
    */
   private boolean isPlayerRegistered(String playerId) {
      for (final PlayerInfo each : roster) {
         if (each.getId().equals(playerId)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Instructs the framework to schedule this team season with the given game. An exception is
    * thrown if the game is already scheduled or the team has a scheduling conflict.
    *
    * @param dto
    */
   public void scheduleEvent(GameDTO dto) {
      if (alreadyScheduled(dto)) {
         throw new EventAlreadyScheduledException();
      }
      if (scheduleConflicts(dto)) {
         throw new EventScheduleConflictException();
      }
      apply(new GameScheduled(id, dto));
   }

   /**
    * Returns true if this team season is already scheduled for the given game.
    *
    * @param dto
    * @return
    */
   public boolean alreadyScheduled(GameDTO dto) {
      for (final GameInfo event : events) {
         if (event.getEventId().equals(dto.getId())) {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns true if the given game conflicts with an already scheduled event, false otherwise.
    *
    * @param dto
    * @return
    */
   private boolean scheduleConflicts(GameDTO dto) {
      for (final GameInfo event : events) {
         if (!event.getEventId().equals(dto.getId()) &&
            event.getStartsAt().equals(dto.getStartsAt())) {
            return true;
         }
      }
      return false;
   }

   /**
    * Instructs the framework to update and persist the game associated with the team season with
    * information contained in the given event.
    *
    * @param dto
    */
   public void updateEvent(GameDTO dto) {
      if (scheduleConflicts(dto)) {
         throw new EventScheduleConflictException();
      }
      apply(new GameRevised(id, dto));
   }

   /**
    * Stores and persists information for a newly created team season.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamSeasonCreated event) {
      final TeamSeasonDTO dto = event.getTeamSeasonDTO();
      id = dto.getTeamSeasonId();
      teamId = dto.getTeam().getId();
      seasonId = dto.getSeason().getId();
      status = dto.getStatus();
      startsOn = dto.getStartsOn();
      endsOn = dto.getEndsOn();
   }

   /**
    * Updates and persists the state of this team season with information contained in the given
    * event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamSeasonUpdated event) {
      final TeamSeasonDTO dto = event.getTeamSeasonDTO();
      teamId = dto.getTeam().getId();
      seasonId = dto.getSeason().getId();
      status = dto.getStatus();
      startsOn = dto.getStartsOn();
      endsOn = dto.getEndsOn();
   }

   /**
    * Marks this team season for deletion.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamSeasonDeleted event) {
      markDeleted();
   }

   /**
    * Creates and persists a value object representing a player newly added to this team season
    * roster.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PlayerRegistered event) {
      final PlayerDTO dto = event.getPlayerDTO();
      final String playerId = dto.getId().toString();
      final String personId = dto.getPerson().getId();

      final PlayerInfo player =
         new PlayerInfo(playerId, personId, dto.getRole(), dto.getStatus(), dto.getJerseyNumber(),
            dto.getPosition());
      roster.add(player);
   }

   /**
    * Updates and persists the value object representing an existing player on this team season
    * roster.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PlayerEdited event) {
      final PlayerDTO dto = event.getPlayerDTO();
      final String playerId = dto.getId().toString();
      final String personId = dto.getPerson().getId();

      final PlayerInfo player =
         new PlayerInfo(playerId, personId, dto.getRole(), dto.getStatus(), dto.getJerseyNumber(),
            dto.getPosition());
      roster.add(player);
   }

   /**
    * Removes the value object representing a player with an aggregate identifier matching
    * information contained in the given event from this team season roster.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PlayerDropped event) {
      final String playerId = event.getPlayerId().toString();
      roster.remove(playerId);
   }

   /**
    * Creates and persists a value object representing a newly scheduled game for this team season.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GameScheduled event) {
      final GameDTO dto = event.getEvent();
      String siteId = null;
      String teamOneId = null;
      String teamTwoId = null;

      if (dto.getSite() != null) {
         siteId = dto.getSite().getId();
      }
      if (dto.getTeamOne() != null) {
         teamOneId = dto.getTeamOne().getId();
      }
      if (dto.getTeamTwo() != null) {
         teamTwoId = dto.getTeamTwo().getId();
      }
      events.add(new GameInfo(dto.getId(), siteId, teamOneId, teamTwoId, dto.getStartsAt()));
   }

   /**
    * Updates and persists the value object that represents an existing game with information
    * contained in the given event. If the game is not yet associated with the team season, it is
    * added.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(GameRevised event) {
      final GameDTO dto = event.getEventDTO();
      String siteId = null;
      String teamOneId = null;
      String teamTwoId = null;

      if (dto.getSite() != null) {
         siteId = dto.getSite().getId();
      }
      if (dto.getTeamOne() != null) {
         teamOneId = dto.getTeamOne().getId();
      }
      if (dto.getTeamTwo() != null) {
         teamTwoId = dto.getTeamTwo().getId();
      }

      final GameInfo vo =
         new GameInfo(dto.getId(), siteId, teamOneId, teamTwoId, dto.getStartsAt());

      boolean found = false;
      for (GameInfo each : events) {
         if (each.getEventId().equals(dto.getId())) {
            each = vo;
            found = true;
         }
      }
      if (!found) {
         events.add(vo);
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public TeamSeasonId getIdentifier() {
      return id;
   }

   /**
    * Returns the aggregate identifier of the parent {@code Team}
    *
    * @return
    */
   public String getTeamId() {
      return teamId;
   }

   /**
    * Returns the aggregate identifier of the associated {@code Season}
    *
    * @return
    */
   public String getSeasonId() {
      return seasonId;
   }

   /**
    * Returns the team status this season.
    *
    * @return
    */
   public TeamStatus getStatus() {
      return status;
   }

   /**
    * Returns the season start date for thsi team.
    *
    * @return
    */
   public LocalDate getStartsOn() {
      return startsOn;
   }

   /**
    * Returns the season ending date for this team.
    *
    * @return
    */
   public LocalDate getEndsOn() {
      return endsOn;
   }

   /**
    * Returns the roster, or collection of players associated with this team for this season.
    *
    * @return
    */
   public List<PlayerInfo> getRoster() {
      return roster;
   }

   /**
    * Returns the collection of games in which the team has played or is scheduled this season.
    *
    * @return
    */
   public List<GameInfo> getEvents() {
      return events;
   }
}
