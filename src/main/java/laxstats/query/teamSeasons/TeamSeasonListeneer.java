package laxstats.query.teamSeasons;

import laxstats.api.teamSeasons.TeamSeasonCreatedEvent;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonDeletedEvent;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamSeasonUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class TeamSeasonListeneer {
	private TeamSeasonQueryRepository repository;

	@Autowired
	public void setRepository(TeamSeasonQueryRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	protected void handle(TeamSeasonCreatedEvent event) {
		final TeamSeasonId identifier = event.getTeamSeasonId();
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();

		final TeamSeasonEntry season = new TeamSeasonEntry(dto.getTeam(),
				dto.getSeason());
		season.setCreatedAt(dto.getCreatedAt());
		season.setCreatedBy(dto.getCreatedBy());
		season.setEndsOn(dto.getEndsOnd());
		season.setModifiedAt(dto.getModifiedAt());
		season.setModifiedBy(dto.getModifiedBy());
		season.setStartsOn(dto.getStartsOn());
		season.setStatus(dto.getStatus());
		repository.save(season);
	}

	@EventHandler
	protected void handle(TeamSeasonUpdatedEvent event) {
		final TeamSeasonId identifier = event.getTeamSeasonId();
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();

		final TeamSeasonEntry season = repository
				.findOne(identifier.toString());
		season.setEndsOn(dto.getEndsOnd());
		season.setModifiedAt(dto.getModifiedAt());
		season.setModifiedBy(dto.getModifiedBy());
		season.setStartsOn(dto.getStartsOn());
		season.setStatus(dto.getStatus());
		repository.save(season);
	}

	@EventHandler
	protected void handle(TeamSeasonDeletedEvent event) {
		final TeamSeasonId identifier = event.getTeamSeasonId();
		final TeamSeasonEntry season = repository
				.findOne(identifier.toString());
		repository.delete(season);
	}
}
