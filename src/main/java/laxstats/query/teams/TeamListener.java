package laxstats.query.teams;

import laxstats.api.teams.TeamCreated;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamDeleted;
import laxstats.api.teams.TeamPasswordCreated;
import laxstats.api.teams.TeamPasswordUpdated;
import laxstats.api.teams.TeamUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code TeamListener} manages events that write to the query database.
 */
@Component
public class TeamListener {
   private TeamQueryRepository teamRepository;

   @Autowired
   public void setTeamRepository(TeamQueryRepository teamRepository) {
      this.teamRepository = teamRepository;
   }

   /**
    * Creates and persists a {@code TeamEntry} with information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(TeamCreated event) {
      final TeamDTO dto = event.getTeamDTO();

      final TeamEntry team = new TeamEntry();
      team.setId(event.getTeamId().toString());
      team.setSponsor(dto.getSponsor());
      team.setName(dto.getName());
      team.setAbbreviation(dto.getAbbreviation());
      team.setGender(dto.getGender());
      team.setLetter(dto.getLetter());
      team.setRegion(dto.getRegion());
      team.setLeague(dto.getLeague());
      team.setHomeSite(dto.getHomeSite());
      team.setCreatedAt(dto.getCreatedAt());
      team.setCreatedBy(dto.getCreatedBy());
      team.setModifiedAt(dto.getModifiedAt());
      team.setModifiedBy(dto.getModifiedBy());

      teamRepository.save(team);
   }

   /**
    * Updates and persists an existing {@code TeamEntry} from information contained in the given
    * event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(TeamUpdated event) {
      final TeamDTO dto = event.getTeamDTO();
      final String id = event.getTeamId().toString();

      final TeamEntry team = teamRepository.findOne(id);
      team.setSponsor(dto.getSponsor());
      team.setName(dto.getName());
      team.setAbbreviation(dto.getAbbreviation());
      team.setGender(dto.getGender());
      team.setLetter(dto.getLetter());
      team.setRegion(dto.getRegion());
      team.setLeague(dto.getLeague());
      team.setHomeSite(dto.getHomeSite());
      team.setModifiedAt(dto.getModifiedAt());
      team.setModifiedBy(dto.getModifiedBy());

      teamRepository.save(team);
   }

   /**
    * Deletes an existing team matching the primary key contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(TeamDeleted event) {
      teamRepository.delete(event.getTeamId().toString());
   }

   /**
    * Updates an existing {@code TeamEntry} with password information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(TeamPasswordCreated event) {
      final TeamDTO dto = event.getTeamDTO();
      final String id = event.getTeamId().toString();

      final TeamEntry team = teamRepository.findOne(id);
      team.setEncodedPassword(dto.getEncodedPassword());
      team.setModifiedAt(dto.getModifiedAt());
      team.setModifiedBy(dto.getModifiedBy());

      teamRepository.save(team);
   }

   /**
    * Updates an existing {@code TeamEntry} with an updated password from information contained in
    * the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(TeamPasswordUpdated event) {
      final TeamDTO dto = event.getTeamDTO();
      final String id = event.getTeamId().toString();

      final TeamEntry team = teamRepository.findOne(id);
      team.setEncodedPassword(dto.getEncodedPassword());
      team.setModifiedAt(dto.getModifiedAt());
      team.setModifiedBy(dto.getModifiedBy());

      teamRepository.save(team);
   }
}
