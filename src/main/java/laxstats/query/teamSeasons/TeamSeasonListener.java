package laxstats.query.teamSeasons;

import laxstats.api.teamSeasons.TeamSeasonCreatedEvent;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonDeletedEvent;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamSeasonUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class TeamSeasonListener {
	private TeamSeasonQueryRepository repository;

	@Autowired
	public void setRepository(TeamSeasonQueryRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	protected void handle(TeamSeasonCreatedEvent event) {
		final TeamSeasonId identifier = event.getTeamSeasonId();
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();

		final TeamSeasonEntry entity = new TeamSeasonEntry();
		entity.setId(identifier.toString());
		entity.setTeam(dto.getTeam());
		entity.setSeason(dto.getSeason());
		entity.setStartsOn(dto.getStartsOn());
		entity.setEndsOn(dto.getEndsOnd());
		entity.setStatus(dto.getStatus());
		entity.setCreatedAt(dto.getCreatedAt());
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setModifiedAt(dto.getModifiedAt());
		entity.setModifiedBy(dto.getModifiedBy());
		repository.save(entity);
	}

	@EventHandler
	protected void handle(TeamSeasonUpdatedEvent event) {
		final TeamSeasonId identifier = event.getTeamSeasonId();
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();

		final TeamSeasonEntry entity = repository
				.findOne(identifier.toString());
		entity.setEndsOn(dto.getEndsOnd());
		entity.setModifiedAt(dto.getModifiedAt());
		entity.setModifiedBy(dto.getModifiedBy());
		entity.setStartsOn(dto.getStartsOn());
		entity.setStatus(dto.getStatus());
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
