package laxstats.query.teamSeasons;

import laxstats.api.teamSeasons.TeamSeasonCreatedEvent;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonDeletedEvent;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamSeasonUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamSeasonListener {
	private TeamSeasonQueryRepository repository;

	@Autowired
	public void setRepository(TeamSeasonQueryRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	protected void handle(TeamSeasonCreatedEvent event) {
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

	@EventHandler
	protected void handle(TeamSeasonUpdatedEvent event) {
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();
		final TeamSeasonId identifier = event.getIdentifier();

		final TeamSeasonEntry entity = repository
				.findOne(identifier.toString());

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

	@EventHandler
	protected void handle(TeamSeasonDeletedEvent event) {
		final TeamSeasonId identifier = event.getTeamSeasonId();
		final TeamSeasonEntry entity = repository
				.findOne(identifier.toString());
		repository.delete(entity);
	}
}
