package laxstats.query.teamSeasons;

import laxstats.api.teamSeasons.TeamSeasonCreated;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonDeleted;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamSeasonUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code TeamSeasonListener} manages events that write to the query database.
 */
@Component
public class TeamSeasonListener {
   private TeamSeasonQueryRepository repository;

   @Autowired
   public void setRepository(TeamSeasonQueryRepository repository) {
      this.repository = repository;
   }

   /**
    * Creates and persists a new team season from information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(TeamSeasonCreated event) {
      final TeamSeasonDTO dto = event.getTeamSeasonDTO();
      final TeamSeasonId identifier = event.getIdentifier();

      final TeamSeasonEntry entity = new TeamSeasonEntry();
      entity.setId(identifier.toString());
      entity.setTeam(dto.getTeam());
      entity.setSeason(dto.getSeason());
      entity.setLeague(dto.getLeague());
      entity.setStartsOn(dto.getStartsOn());
      entity.setEndsOn(dto.getEndsOn());
      entity.setName(dto.getTeam().getName());
      entity.setStatus(dto.getStatus());
      entity.setCreatedAt(dto.getCreatedAt());
      entity.setCreatedBy(dto.getCreatedBy());
      entity.setModifiedAt(dto.getModifiedAt());
      entity.setModifiedBy(dto.getModifiedBy());

      repository.save(entity);
   }

   /**
    * Updates and persists an existing team season with information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(TeamSeasonUpdated event) {
      final TeamSeasonDTO dto = event.getTeamSeasonDTO();
      final TeamSeasonId identifier = event.getIdentifier();

      final TeamSeasonEntry entity = repository.findOne(identifier.toString());

      entity.setSeason(dto.getSeason());
      entity.setLeague(dto.getLeague());
      entity.setStartsOn(dto.getStartsOn());
      entity.setEndsOn(dto.getEndsOn());
      entity.setName(dto.getTeam().getName());
      entity.setStatus(dto.getStatus());
      entity.setModifiedAt(dto.getModifiedAt());
      entity.setModifiedBy(dto.getModifiedBy());

      repository.save(entity);
   }

   /**
    * Deletes the existing team season matching the identifier contained the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(TeamSeasonDeleted event) {
      final TeamSeasonId identifier = event.getTeamSeasonId();
      final TeamSeasonEntry entity = repository.findOne(identifier.toString());
      repository.delete(entity);
   }
}
