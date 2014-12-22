package laxstats.domain.teamSeasons;

import laxstats.api.teamSeasons.CreateTeamSeasonCommand;
import laxstats.api.teamSeasons.DeleteTeamSeasonCommand;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.UpdateTeamSeasonCommand;

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
	protected TeamSeasonId handle(CreateTeamSeasonCommand command) {
		final TeamSeasonId identifier = command.getTeamSeasonId();
		final TeamSeason season = new TeamSeason(command.getTeamSeasonId(),
				command.getTeamSeasonDTO());
		repository.add(season);
		return identifier;
	}

	@CommandHandler
	protected void handle(UpdateTeamSeasonCommand command) {
		final TeamSeasonId identifier = command.getTeamSeasonId();
		final TeamSeason season = repository.load(identifier);
		season.update(command);
	}

	@CommandHandler
	protected void handle(DeleteTeamSeasonCommand command) {
		final TeamSeasonId identifier = command.getTeamSeasonId();
		final TeamSeason season = repository.load(identifier);
		season.delete(command);
	}
}
