package laxstats.domain.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teams.CreateTeamCommand;
import laxstats.api.teams.CreateTeamPasswordCommand;
import laxstats.api.teams.DeleteTeamCommand;
import laxstats.api.teams.EditTeamSeasonCommand;
import laxstats.api.teams.RegisterTeamSeasonCommand;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.UpdateTeamCommand;
import laxstats.api.teams.UpdateTeamPasswordCommand;
import laxstats.domain.teamSeasons.TeamSeason;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandHandler {
	private Repository<Team> repository;
	private Repository<TeamSeason> teamSeasonRepository;

	@Autowired
	@Qualifier("teamRepository")
	public void setTeamRepository(Repository<Team> repository) {
		this.repository = repository;
	}

	@Autowired
	@Qualifier("teamSeasonRepository")
	public void setTeamSeasonRepository(
			Repository<TeamSeason> teamSeasonRepository) {
		this.teamSeasonRepository = teamSeasonRepository;
	}

	/*---------- Team commands ----------*/

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

	/*---------- Team Season commands ----------*/

	@CommandHandler
	protected void handle(RegisterTeamSeasonCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);
		try {
			// Test and create the team value object
			team.registerSeason(command.getTeamSeasonDTO());

			// Create the aggregate
			final TeamSeasonDTO dto = command.getTeamSeasonDTO();
			final TeamSeason entity = new TeamSeason(dto.getTeamSeasonId(), dto);
			teamSeasonRepository.add(entity);
		} catch (final Exception e) {
			// TODO: handle exception
		}
	}

	@CommandHandler
	protected void handle(EditTeamSeasonCommand command) {
		// Update the team value object
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);
		team.updateSeason(command.getTeamSeasonDTO());

		// Update the aggregate
		final TeamSeasonDTO dto = command.getTeamSeasonDTO();
		final TeamSeason teamSeason = teamSeasonRepository.load(dto
				.getTeamSeasonId());
		teamSeason.update(dto.getTeamSeasonId(), dto);
	}

	/*---------- Password commands ----------*/

	@CommandHandler
	protected void handle(CreateTeamPasswordCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);
		team.createPassword(identifier, command.getTeamDTO());
	}

	@CommandHandler
	protected void handle(UpdateTeamPasswordCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team team = repository.load(identifier);
		team.updatePassword(identifier, command.getTeamDTO());
	}

}
