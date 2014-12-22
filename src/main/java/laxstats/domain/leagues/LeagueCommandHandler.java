package laxstats.domain.leagues;

import laxstats.api.leagues.CreateLeagueCommand;
import laxstats.api.leagues.DeleteLeagueCommand;
import laxstats.api.leagues.LeagueId;
import laxstats.api.leagues.RegisterAffiliateLeagueCommand;
import laxstats.api.leagues.RegisterTeamCommand;
import laxstats.api.leagues.UpdateLeagueCommand;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LeagueCommandHandler {
	private static Logger logger = LoggerFactory
			.getLogger(LeagueCommandHandler.class);
	private Repository<League> repository;

	@Autowired
	@Qualifier("leagueRepository")
	public void setRepository(Repository<League> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public LeagueId handle(CreateLeagueCommand command) {
		final LeagueId identifier = command.getLeagueId();
		final League league = new League(identifier, command.getLeagueDTO());
		repository.add(league);
		return identifier;
	}

	@CommandHandler
	public void handle(UpdateLeagueCommand command) {
		final LeagueId identifier = command.getLeagueId();
		final League league = repository.load(identifier);
		league.update(identifier, command.getLeagueDTO());
	}

	@CommandHandler
	public void handle(DeleteLeagueCommand command) {
		final LeagueId leagueId = command.getLeagueId();
		final League league = repository.load(leagueId);
		league.delete(leagueId);
	}

	@CommandHandler
	public void handle(RegisterAffiliateLeagueCommand command) {
		final LeagueId identifier = command.getLeagueId();
		final League league = repository.load(identifier);
		league.registerAffiliate(command.getAffiliateId().toString());
	}

	@CommandHandler
	public void handle(RegisterTeamCommand command) {
		final LeagueId identifier = command.getLeagueId();
		final League league = repository.load(identifier);
		league.registerTeam(command.getTeam());
	}
}
