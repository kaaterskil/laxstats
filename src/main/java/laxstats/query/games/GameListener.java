package laxstats.query.games;

import java.util.List;

import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.AttendeeDeletedEvent;
import laxstats.api.games.AttendeeRegisteredEvent;
import laxstats.api.games.AttendeeUpdatedEvent;
import laxstats.api.games.GameCreatedEvent;
import laxstats.api.games.GameDTO;
import laxstats.api.games.GameDeletedEvent;
import laxstats.api.games.GameId;
import laxstats.api.games.GameUpdatedEvent;

import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameListener {
   private GameQueryRepository repository;

   @Autowired
   public void setRepository(GameQueryRepository repository) {
      this.repository = repository;
   }

   @EventHandler
   protected void handle(GameCreatedEvent event) {
      final GameId gameId = event.getEventId();
      final GameDTO dto = event.getEventDTO();

      final GameEntry entity = new GameEntry();
      entity.setId(gameId.toString());
      entity.setSite(dto.getSite());
      entity.setAlignment(dto.getAlignment());
      entity.setStartsAt(dto.getStartsAt());
      entity.setSchedule(dto.getSchedule());
      entity.setStatus(dto.getStatus());
      entity.setConditions(dto.getConditions());
      entity.setDescription(dto.getDescription());
      entity.setCreatedAt(dto.getCreatedAt());
      entity.setCreatedBy(dto.getCreatedBy());
      entity.setModifiedAt(dto.getModifiedAt());
      entity.setModifiedBy(dto.getModifiedBy());

      final String teamOneId = IdentifierFactory.getInstance().generateIdentifier();
      final TeamEvent teamOne = new TeamEvent(teamOneId, dto.getTeamOne(), entity, 0);
      teamOne.setAlignment(dto.getTeamOneAlignment());
      teamOne.setCreatedAt(dto.getCreatedAt());
      teamOne.setCreatedBy(dto.getCreatedBy());
      teamOne.setModifiedAt(dto.getModifiedAt());
      teamOne.setModifiedBy(dto.getModifiedBy());

      final String teamTwoId = IdentifierFactory.getInstance().generateIdentifier();
      final TeamEvent teamTwo = new TeamEvent(teamTwoId, dto.getTeamTwo(), entity, 1);
      teamTwo.setAlignment(dto.getTeamTwoAlignment());
      teamTwo.setCreatedAt(dto.getCreatedAt());
      teamTwo.setCreatedBy(dto.getCreatedBy());
      teamTwo.setModifiedAt(dto.getModifiedAt());
      teamTwo.setModifiedBy(dto.getModifiedBy());

      repository.save(entity);
   }

   @EventHandler
   protected void handle(GameUpdatedEvent event) {
      final GameId gameId = event.getEventId();
      final GameDTO dto = event.getEventDTO();

      final GameEntry entity = repository.findOne(gameId.toString());
      entity.setSite(dto.getSite());
      entity.setAlignment(dto.getAlignment());
      entity.setStartsAt(dto.getStartsAt());
      entity.setSchedule(dto.getSchedule());
      entity.setStatus(dto.getStatus());
      entity.setConditions(dto.getConditions());
      entity.setDescription(dto.getDescription());
      entity.setModifiedAt(dto.getModifiedAt());
      entity.setModifiedBy(dto.getModifiedBy());

      final List<TeamEvent> teams = entity.getTeams();
      TeamEvent teamOne = null;
      if (teams.size() == 0) {
         final String teamOneId = IdentifierFactory.getInstance().generateIdentifier();
         teamOne = new TeamEvent(teamOneId, dto.getTeamOne(), entity, 0);
         teamOne.setCreatedAt(dto.getCreatedAt());
         teamOne.setCreatedBy(dto.getCreatedBy());
      }
      else {
         teamOne = teams.get(0);
         teamOne.setTeamSeason(dto.getTeamOne());
      }
      teamOne.setAlignment(dto.getTeamOneAlignment());
      teamOne.setModifiedAt(dto.getModifiedAt());
      teamOne.setModifiedBy(dto.getModifiedBy());

      TeamEvent teamTwo = null;
      if (teams.size() < 2) {
         final String teamTwoId = IdentifierFactory.getInstance().generateIdentifier();
         teamTwo = new TeamEvent(teamTwoId, dto.getTeamTwo(), entity, 1);
         teamTwo.setCreatedAt(dto.getCreatedAt());
         teamTwo.setCreatedBy(dto.getCreatedBy());
      }
      else {
         teamTwo = teams.get(1);
         teamTwo.setTeamSeason(dto.getTeamTwo());
      }
      teamTwo.setAlignment(dto.getTeamTwoAlignment());
      teamTwo.setModifiedAt(dto.getModifiedAt());
      teamTwo.setModifiedBy(dto.getModifiedBy());

      repository.save(entity);
   }

   @EventHandler
   protected void handle(GameDeletedEvent event) {
      final GameId gameId = event.getEventId();
      final GameEntry entity = repository.findOne(gameId.toString());
      repository.delete(entity);
   }

   /* ---------- Game Attendees ---------- */

   @EventHandler
   protected void handle(AttendeeRegisteredEvent event) {
      final GameId identifier = event.getEventId();
      final GameEntry aggregate = repository.findOne(identifier.toString());

      final AttendeeDTO dto = event.getAttendeeDTO();
      final String attendeeId = dto.getId();
      final AttendeeEntry entity =
         new AttendeeEntry(aggregate, dto.getPlayer(), dto.getTeamSeason());
      entity.setId(attendeeId);
      entity.setName(dto.getName());
      entity.setJerseyNumber(dto.getJerseyNumber());
      entity.setRole(dto.getRole());
      entity.setStatus(dto.getStatus());
      entity.setCreatedAt(dto.getCreatedAt());
      entity.setCreatedBy(dto.getCreatedBy());
      entity.setModifiedAt(dto.getModifiedAt());
      entity.setModifiedBy(dto.getModifiedBy());

      aggregate.getEventAttendees().put(attendeeId, entity);
      repository.save(aggregate);
   }

   @EventHandler
   protected void handle(AttendeeUpdatedEvent event) {
      final GameId identifier = event.getEventId();
      final GameEntry aggregate = repository.findOne(identifier.toString());

      final AttendeeDTO dto = event.getAttendeeDTO();
      final AttendeeEntry entity = aggregate.getEventAttendees().get(dto.getId());
      entity.setName(dto.getName());
      entity.setJerseyNumber(dto.getJerseyNumber());
      entity.setRole(dto.getRole());
      entity.setStatus(dto.getStatus());
      entity.setModifiedAt(dto.getModifiedAt());
      entity.setModifiedBy(dto.getModifiedBy());
      repository.save(aggregate);
   }

   @EventHandler
   protected void handle(AttendeeDeletedEvent event) {
      final GameId identifier = event.getEventId();
      final GameEntry aggregate = repository.findOne(identifier.toString());
      aggregate.getEventAttendees().remove(event.getAttendeeId());
      repository.save(aggregate);
   }
}
