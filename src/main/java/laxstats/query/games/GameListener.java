package laxstats.query.games;

import java.util.List;

import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.AttendeeDeleted;
import laxstats.api.games.AttendeeRegistered;
import laxstats.api.games.AttendeeUpdated;
import laxstats.api.games.GameCreated;
import laxstats.api.games.GameDTO;
import laxstats.api.games.GameDeleted;
import laxstats.api.games.GameId;
import laxstats.api.games.GameUpdated;

import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code GameListener} manages events that write to the query database.
 */
@Component
public class GameListener {
   private GameQueryRepository repository;

   @Autowired
   public void setRepository(GameQueryRepository repository) {
      this.repository = repository;
   }

   /**
    * Creates and persists a new game from information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GameCreated event) {
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

   /**
    * Updates and persists changes in the state of an existing game with information contained in
    * the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GameUpdated event) {
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

      // Create or update team one.
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

      // Create or update team two.
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

   /**
    * Deletes the game matching the aggregate identifier contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GameDeleted event) {
      final GameId gameId = event.getEventId();
      final GameEntry entity = repository.findOne(gameId.toString());
      repository.delete(entity);
   }

   /**
    * Creates and persists a new game attendee from information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(AttendeeRegistered event) {
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

   /**
    * Updates and persists changes to the state of an existing game attendee with information
    * contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(AttendeeUpdated event) {
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

   /**
    * Deletes the game attendee matching the identifier contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(AttendeeDeleted event) {
      final GameId identifier = event.getEventId();
      final GameEntry aggregate = repository.findOne(identifier.toString());
      aggregate.getEventAttendees().remove(event.getAttendeeId());
      repository.save(aggregate);
   }
}
