package laxstats.query.leagues;

import laxstats.api.leagues.AffiliateLeagueRegisteredEvent;
import laxstats.api.leagues.LeagueCreatedEvent;
import laxstats.api.leagues.LeagueDTO;
import laxstats.api.leagues.LeagueDeletedEvent;
import laxstats.api.leagues.LeagueId;
import laxstats.api.leagues.LeagueUpdatedEvent;
import laxstats.api.leagues.TeamRegisteredEvent;
import laxstats.domain.leagues.TeamInfo;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LeagueListener {
	private LeagueQueryRepository repository;
	private TeamQueryRepository teamRepository;

	@Autowired
	public void setRepository(LeagueQueryRepository repository) {
		this.repository = repository;
	}

	@Autowired
	public void setTeamRepository(TeamQueryRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@EventHandler
	protected void handle(LeagueCreatedEvent event) {
		final LeagueDTO dto = event.getLeagueDTO();

		final LeagueEntry league = new LeagueEntry();
		league.setId(event.getLeagueId().toString());
		league.setName(dto.getName());
		league.setLevel(dto.getLevel());
		league.setParent(dto.getParent());
		league.setCreatedAt(dto.getCreatedAt());
		league.setCreatedBy(dto.getCreatedBy());
		league.setModifiedAt(dto.getModifiedAt());
		league.setModifiedBy(dto.getModifiedBy());
		repository.save(league);
	}

	@EventHandler
	protected void handle(LeagueUpdatedEvent event) {
		final LeagueDTO dto = event.getLeagueDTO();
		final LeagueEntry league = repository.findOne(event.getLeagueId()
				.toString());

		league.setName(dto.getName());
		league.setLevel(dto.getLevel());
		league.setParent(dto.getParent());
		league.setModifiedAt(dto.getModifiedAt());
		league.setModifiedBy(dto.getModifiedBy());
		repository.save(league);
	}

	@EventHandler
	protected void handle(LeagueDeletedEvent event) {
		final LeagueEntry league = repository.findOne(event.getLeagueId()
				.toString());
		repository.delete(league);
	}

	@EventHandler
	protected void handle(AffiliateLeagueRegisteredEvent event) {
		final LeagueEntry league = repository.findOne(event.getLeagueId()
				.toString());
		final LeagueEntry affiliate = repository
				.findOne(event.getAffiliateId());
		league.addChild(affiliate);
		repository.save(league);
	}

	@EventHandler
	protected void handle(TeamRegisteredEvent event) {
		final LeagueId identifier = event.getLeagueId();
		final TeamInfo dto = event.getTeam();

		final LeagueEntry league = repository.findOne(identifier.toString());
		final TeamEntry team = teamRepository.findOne(dto.getId());

		final TeamAffiliation teamAffiliation = new TeamAffiliation(team,
				league);
		teamAffiliation.setStartingOn(dto.getStartingOn());
		teamAffiliation.setEndingOn(dto.getEndingOn());
		repository.save(league);
	}
}
