package laxstats.query.teams;

import laxstats.api.teams.TeamCreatedEvent;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamDeletedEvent;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.TeamPasswordCreatedEvent;
import laxstats.api.teams.TeamPasswordUpdatedEvent;
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
		entry.setId(event.getTeamId().toString());
		entry.setName(dto.getName());
		entry.setGender(dto.getGender());
		entry.setHomeSite(dto.getHomeSite());
		entry.setEncodedPassword(dto.getEncodedPassword());
		entry.setCreatedAt(dto.getCreatedAt());
		entry.setCreatedBy(dto.getCreatedBy());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
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

	@EventHandler
	protected void handle(TeamPasswordCreatedEvent event) {
		final TeamId identifier = event.getTeamId();
		final TeamEntry entry = teamRepository.findOne(identifier.toString());

		final TeamDTO dto = event.getTeamDTO();
		entry.setEncodedPassword(dto.getEncodedPassword());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		teamRepository.save(entry);
	}

	@EventHandler
	protected void handle(TeamPasswordUpdatedEvent event) {
		final TeamId identifier = event.getTeamId();
		final TeamEntry entry = teamRepository.findOne(identifier.toString());

		final TeamDTO dto = event.getTeamDTO();
		entry.setEncodedPassword(dto.getEncodedPassword());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		teamRepository.save(entry);
	}
}
