package laxstats.query.teams;

import laxstats.api.teams.TeamCreatedEvent;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamDeletedEvent;
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

		final TeamEntry team = new TeamEntry();
		team.setId(event.getTeamId().toString());
		team.setSponsor(dto.getSponsor());
		team.setName(dto.getName());
		team.setAbbreviation(dto.getAbbreviation());
		team.setGender(dto.getGender());
		team.setLetter(dto.getLetter());
		team.setRegion(dto.getRegion());
		team.setAffiliation(dto.getAffiliation());
		team.setHomeSite(dto.getHomeSite());
		team.setCreatedAt(dto.getCreatedAt());
		team.setCreatedBy(dto.getCreatedBy());
		team.setModifiedAt(dto.getModifiedAt());
		team.setModifiedBy(dto.getModifiedBy());

		teamRepository.save(team);
	}

	@EventHandler
	protected void handle(TeamUpdatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		final String id = event.getTeamId().toString();

		final TeamEntry team = teamRepository.findOne(id);
		team.setSponsor(dto.getSponsor());
		team.setName(dto.getName());
		team.setAbbreviation(dto.getAbbreviation());
		team.setGender(dto.getGender());
		team.setLetter(dto.getLetter());
		team.setRegion(dto.getRegion());
		team.setAffiliation(dto.getAffiliation());
		team.setHomeSite(dto.getHomeSite());
		team.setModifiedAt(dto.getModifiedAt());
		team.setModifiedBy(dto.getModifiedBy());

		teamRepository.save(team);
	}

	@EventHandler
	protected void handle(TeamDeletedEvent event) {
		teamRepository.delete(event.getTeamId().toString());
	}

	@EventHandler
	protected void handle(TeamPasswordCreatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		final String id = event.getTeamId().toString();

		final TeamEntry team = teamRepository.findOne(id);
		team.setEncodedPassword(dto.getEncodedPassword());
		team.setModifiedAt(dto.getModifiedAt());
		team.setModifiedBy(dto.getModifiedBy());

		teamRepository.save(team);
	}

	@EventHandler
	protected void handle(TeamPasswordUpdatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		final String id = event.getTeamId().toString();

		final TeamEntry team = teamRepository.findOne(id);
		team.setEncodedPassword(dto.getEncodedPassword());
		team.setModifiedAt(dto.getModifiedAt());
		team.setModifiedBy(dto.getModifiedBy());

		teamRepository.save(team);
	}
}
