package laxstats.query.teams;

import laxstats.api.teams.TeamCreatedEvent;
import laxstats.api.teams.TeamDTO;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamListener {

	private TeamQueryRepository teamRepository;

	@Autowired
	public void setTeamRepository(TeamQueryRepository teamRepository) {
		this.teamRepository = teamRepository;
	}
	
	@EventHandler
	protected final void handle(TeamCreatedEvent event) {
		TeamDTO dto = event.getTeamDTO();
		
		Team team = new Team();
		team.setId(event.getTeamId().toString());
		
		team.setCreatedAt(dto.getCreatedAt());
		team.setCreatedBy(dto.getCreatedBy());
		team.setEncryptedPassword(dto.getEncryptedPassword());
		team.setGender(dto.getGender());
		team.setHomeSite(dto.getHomeSiteId());
		team.setModifiedAt(dto.getModifiedAt());
		team.setModifiedBy(dto.getModifiedBy());
		team.setName(dto.getName());
		
		teamRepository.save(team);
	}
}
