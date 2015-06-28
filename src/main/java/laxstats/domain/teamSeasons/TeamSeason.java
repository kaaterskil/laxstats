package laxstats.domain.teamSeasons;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.games.GameDTO;
import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerId;
import laxstats.api.teamSeasons.DeleteTeamSeasonCommand;
import laxstats.api.teamSeasons.EventAlreadyScheduledException;
import laxstats.api.teamSeasons.EventRevisedEvent;
import laxstats.api.teamSeasons.EventScheduleConflictException;
import laxstats.api.teamSeasons.EventScheduledEvent;
import laxstats.api.teamSeasons.PlayerDroppedEvent;
import laxstats.api.teamSeasons.PlayerEditedEvent;
import laxstats.api.teamSeasons.PlayerRegisteredEvent;
import laxstats.api.teamSeasons.TeamSeasonCreatedEvent;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonDeletedEvent;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamSeasonUpdatedEvent;
import laxstats.api.teamSeasons.TeamStatus;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDate;

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
   private final List<EventInfo> events = new ArrayList<>();

   public TeamSeason(TeamSeasonId teamSeasonId, TeamSeasonDTO teamSeasonDTO) {
      apply(new TeamSeasonCreatedEvent(teamSeasonId, teamSeasonDTO));
   }

   protected TeamSeason() {
   }

   /*---------- Methods ----------*/

   public void update(TeamSeasonId identifier, TeamSeasonDTO dto) {
      apply(new TeamSeasonUpdatedEvent(identifier, dto));
   }

   public void delete(DeleteTeamSeasonCommand command) {
      apply(new TeamSeasonDeletedEvent(command.getTeamSeasonId()));
   }

   public void registerPlayer(PlayerDTO dto) {
      apply(new PlayerRegisteredEvent(id, dto));
   }

   public boolean canRegisterPlayer(String playerId) {
      return !isPlayerRegistered(playerId);
   }

   public void updatePlayer(PlayerDTO dto) {
      apply(new PlayerEditedEvent(id, dto));
   }

   public void dropPlayer(PlayerId playerId) {
      if (!isPlayerRegistered(playerId.toString())) {
         throw new IllegalArgumentException("player is not registered");
      }
      apply(new PlayerDroppedEvent(id, playerId));
   }

   private boolean isPlayerRegistered(String playerId) {
      for (final PlayerInfo each : roster) {
         if (each.getId().equals(playerId)) {
            return true;
         }
      }
      return false;
   }

   public void scheduleEvent(GameDTO dto) {
      if (alreadyScheduled(dto)) {
         throw new EventAlreadyScheduledException();
      }
      if (scheduleConflicts(dto)) {
         throw new EventScheduleConflictException();
      }
      apply(new EventScheduledEvent(id, dto));
   }

   public boolean alreadyScheduled(GameDTO dto) {
      for (final EventInfo event : events) {
         if (event.getEventId().equals(dto.getId())) {
            return true;
         }
      }
      return false;
   }

   private boolean scheduleConflicts(GameDTO dto) {
      for (final EventInfo event : events) {
         if (!event.getEventId().equals(dto.getId()) &&
            event.getStartsAt().equals(dto.getStartsAt())) {
            return true;
         }
      }
      return false;
   }

   public void updateEvent(GameDTO dto) {
      if (scheduleConflicts(dto)) {
         throw new EventScheduleConflictException();
      }
      apply(new EventRevisedEvent(id, dto));
   }

   /*---------- Event handlers ----------*/

   @EventSourcingHandler
   protected void handle(TeamSeasonCreatedEvent event) {
      final TeamSeasonDTO dto = event.getTeamSeasonDTO();
      id = dto.getTeamSeasonId();
      teamId = dto.getTeam().getId();
      seasonId = dto.getSeason().getId();
      status = dto.getStatus();
      startsOn = dto.getStartsOn();
      endsOn = dto.getEndsOn();
   }

   @EventSourcingHandler
   protected void handle(TeamSeasonUpdatedEvent event) {
      final TeamSeasonDTO dto = event.getTeamSeasonDTO();
      teamId = dto.getTeam().getId();
      seasonId = dto.getSeason().getId();
      status = dto.getStatus();
      startsOn = dto.getStartsOn();
      endsOn = dto.getEndsOn();
   }

   @EventSourcingHandler
   protected void handle(TeamSeasonDeletedEvent event) {
      markDeleted();
   }

   @EventSourcingHandler
   protected void handle(PlayerRegisteredEvent event) {
      final PlayerDTO dto = event.getPlayerDTO();
      final String playerId = dto.getId().toString();
      final String personId = dto.getPerson().getId();

      final PlayerInfo player =
         new PlayerInfo(playerId, personId, dto.getRole(), dto.getStatus(), dto.getJerseyNumber(),
            dto.getPosition());
      roster.add(player);
   }

   @EventSourcingHandler
   protected void handle(PlayerEditedEvent event) {
      final PlayerDTO dto = event.getPlayerDTO();
      final String playerId = dto.getId().toString();
      final String personId = dto.getPerson().getId();

      final PlayerInfo player =
         new PlayerInfo(playerId, personId, dto.getRole(), dto.getStatus(), dto.getJerseyNumber(),
            dto.getPosition());
      roster.add(player);
   }

   @EventSourcingHandler
   protected void handle(PlayerDroppedEvent event) {
      final String playerId = event.getPlayerId().toString();
      roster.remove(playerId);
   }

   @EventSourcingHandler
   protected void handle(EventScheduledEvent event) {
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
      events.add(new EventInfo(dto.getId(), siteId, teamOneId, teamTwoId, dto.getStartsAt()));
   }

   @EventSourcingHandler
   protected void handle(EventRevisedEvent event) {
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

      final EventInfo vo =
         new EventInfo(dto.getId(), siteId, teamOneId, teamTwoId, dto.getStartsAt());

      boolean found = false;
      for (EventInfo each : events) {
         if (each.getEventId().equals(dto.getId())) {
            each = vo;
            found = true;
         }
      }
      if (!found) {
         events.add(vo);
      }
   }

   /*---------- Getters ----------*/

   @Override
   public TeamSeasonId getIdentifier() {
      return id;
   }

   public String getTeamId() {
      return teamId;
   }

   public String getSeasonId() {
      return seasonId;
   }

   public TeamStatus getStatus() {
      return status;
   }

   public LocalDate getStartsOn() {
      return startsOn;
   }

   public LocalDate getEndsOn() {
      return endsOn;
   }

   public List<PlayerInfo> getRoster() {
      return roster;
   }

   public List<EventInfo> getEvents() {
      return events;
   }
}
