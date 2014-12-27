package laxstats.domain.teamSeasons;

import laxstats.api.teamSeasons.DeleteTeamSeasonCommand;
import laxstats.api.teamSeasons.TeamSeasonId;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TeamSeasonCommandHandler {
	private Repository<TeamSeason> repository;

	@Autowired
	@Qualifier("teamSeasonRepository")
	public void setRepository(Repository<TeamSeason> repository) {
		this.repository = repository;
	}

	@CommandHandler
	protected void handle(DeleteTeamSeasonCommand command) {
		final TeamSeasonId identifier = command.getTeamSeasonId();
		final TeamSeason entity = repository.load(identifier);
		entity.delete(command);
	}
}
