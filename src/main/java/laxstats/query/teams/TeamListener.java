package laxstats.query.teams;

import laxstats.api.teams.TeamCreatedEvent;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamDeletedEvent;
import laxstats.api.teams.TeamUpdatedEvent;

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
	protected void handle(TeamCreatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();

		final TeamEntry entry = new TeamEntry();
		entry.setCreatedAt(dto.getCreatedAt());
		entry.setCreatedBy(dto.getCreatedBy());
		entry.setEncodedPassword(dto.getEncodedPassword());
		entry.setGender(dto.getGender());
		entry.setHomeSite(dto.getHomeSite());
		entry.setId(event.getTeamId().toString());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		entry.setName(dto.getName());

		teamRepository.save(entry);
	}

	@EventHandler
	protected void handle(TeamUpdatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		final TeamEntry entry = teamRepository.findOne(event.getTeamId()
				.toString());

		entry.setEncodedPassword(dto.getEncodedPassword());
		entry.setGender(dto.getGender());
		entry.setHomeSite(dto.getHomeSite());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		entry.setName(dto.getName());

		teamRepository.save(entry);
	}

	@EventHandler
	protected void handle(TeamDeletedEvent event) {
		teamRepository.delete(event.getTeamId().toString());
	}
}
