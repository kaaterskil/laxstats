package laxstats.query.players;

import laxstats.api.players.PlayerCreated;
import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerDeleted;
import laxstats.api.players.PlayerUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code PlayerListener} manages events that write to the query database.
 */
@Component
public class PlayerListener {
   private PlayerQueryRepository repository;

   @Autowired
   public void setRepository(PlayerQueryRepository repository) {
      this.repository = repository;
   }

   /**
    * Creates and persists a player with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PlayerCreated event) {
      final PlayerDTO dto = event.getPlayerDTO();
      final String id = event.getPlayerId()
         .toString();

      final PlayerEntry entity = new PlayerEntry();
      entity.setId(id);
      entity.setPerson(dto.getPerson());
      entity.setTeamSeason(dto.getTeam());
      entity.setFullName(dto.getPerson()
         .getFullName());
      entity.setRole(dto.getRole());
      entity.setStatus(dto.getStatus());
      entity.setJerseyNumber(dto.getJerseyNumber());
      entity.setPosition(dto.getPosition());
      entity.setCaptain(dto.isCaptain());
      entity.setDepth(dto.getDepth());
      entity.setHeight(dto.getHeight());
      entity.setWeight(dto.getWeight());
      entity.setParentReleased(dto.isParentReleased());
      entity.setParentReleaseSentOn(dto.getParentReleaseSentOn());
      entity.setParentReleaseReceivedOn(dto.getParentReleaseReceivedOn());
      entity.setCreatedAt(dto.getCreatedAt());
      entity.setCreatedBy(dto.getCreatedBy());
      entity.setModifiedAt(dto.getModifiedAt());
      entity.setModifiedBy(dto.getModifiedBy());
      repository.save(entity);
   }

   /**
    * Updates a player with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PlayerUpdated event) {
      final PlayerDTO dto = event.getPlayerDTO();
      final String id = event.getPlayerId()
         .toString();

      final PlayerEntry entity = repository.findOne(id);
      entity.setPerson(dto.getPerson());
      entity.setTeamSeason(dto.getTeam());
      entity.setFullName(dto.getPerson()
         .getFullName());
      entity.setRole(dto.getRole());
      entity.setStatus(dto.getStatus());
      entity.setJerseyNumber(dto.getJerseyNumber());
      entity.setPosition(dto.getPosition());
      entity.setCaptain(dto.isCaptain());
      entity.setDepth(dto.getDepth());
      entity.setHeight(dto.getHeight());
      entity.setWeight(dto.getWeight());
      entity.setParentReleased(dto.isParentReleased());
      entity.setParentReleaseSentOn(dto.getParentReleaseSentOn());
      entity.setParentReleaseReceivedOn(dto.getParentReleaseReceivedOn());
      entity.setModifiedAt(dto.getModifiedAt());
      entity.setModifiedBy(dto.getModifiedBy());
      repository.save(entity);
   }

   /**
    * Deletes a player matching the primary key contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PlayerDeleted event) {
      final String id = event.getPlayerId()
         .toString();
      final PlayerEntry entity = repository.findOne(id);
      repository.delete(entity);
   }
}
