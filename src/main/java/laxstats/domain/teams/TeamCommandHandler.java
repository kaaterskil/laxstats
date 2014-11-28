package laxstats.domain.teams;

import laxstats.api.teams.CreateTeamCommand;
import laxstats.api.teams.TeamId;
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
		TeamId identifier = command.getTeamId();
		Team team = new Team(command.getTeamId(), command.getTeamDTO());
		repository.add(team);
		return identifier;
	}
}
