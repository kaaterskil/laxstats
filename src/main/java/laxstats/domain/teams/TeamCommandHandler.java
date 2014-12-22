package laxstats.domain.teams;

import laxstats.api.teams.CreateTeamCommand;
import laxstats.api.teams.CreateTeamPasswordCommand;
import laxstats.api.teams.DeleteTeamCommand;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.UpdateTeamCommand;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandHandler {
	private Repository<Team> repository;

	@Autowired
	@Qualifier("teamRepository")
	public void setRepository(Repository<Team> repository) {
		this.repository = repository;
	}

	@CommandHandler
	protected TeamId handle(CreateTeamCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = new Team(identifier, command.getTeamDTO());
		repository.add(team);
		return identifier;
	}

	@CommandHandler
	protected void handle(UpdateTeamCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);
		team.update(identifier, command.getTeamDTO());
	}

	@CommandHandler
	protected void handle(DeleteTeamCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);
		team.delete(identifier);
	}

	@CommandHandler
	protected void handle(CreateTeamPasswordCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);

	}

}
