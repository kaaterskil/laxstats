package laxstats.domain.teams;

import laxstats.api.teams.CreateTeamCommand;
import laxstats.api.teams.DeleteTeamCommand;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.UpdateTeamCommand;
import laxstats.query.teams.TeamQueryRepository;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandHandler {

	private Repository<Team> repository;

	@SuppressWarnings("unused")
	private TeamQueryRepository teamQueryRepository;

	@Autowired
	@Qualifier("teamRepository")
	public void setRepository(Repository<Team> repository) {
		this.repository = repository;
	}

	@Autowired
	public void setTeamQueryRepository(TeamQueryRepository teamQueryRepository) {
		this.teamQueryRepository = teamQueryRepository;
	}

	@CommandHandler
	public TeamId handle(CreateTeamCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = new Team(identifier, command.getTeamDTO());
		repository.add(team);
		return identifier;
	}

	@CommandHandler
	public void handle(UpdateTeamCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);
		team.update(identifier, command.getTeamDTO());
	}

	@CommandHandler
	public void handle(DeleteTeamCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);
		team.delete(identifier);
	}

}
