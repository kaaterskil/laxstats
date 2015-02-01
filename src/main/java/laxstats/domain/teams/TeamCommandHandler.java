package laxstats.domain.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonId;
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
		final Team aggregate = new Team(identifier, command.getTeamDTO());
		repository.add(aggregate);
		return identifier;
	}

	@CommandHandler
	protected void handle(UpdateTeamCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team aggregate = repository.load(identifier);
		aggregate.update(identifier, command.getTeamDTO());
	}

	@CommandHandler
	protected void handle(DeleteTeamCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team aggregate = repository.load(identifier);
		aggregate.delete(identifier);
	}

	/*---------- Team Season commands ----------*/

	@CommandHandler
	protected TeamSeasonId handle(RegisterTeamSeasonCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team aggregate = repository.load(identifier);

		final TeamSeasonDTO dto = command.getTeamSeasonDTO();
		final TeamSeasonId teamSeasonId = dto.getTeamSeasonId();
		final String seasonId = dto.getSeason().getId();

		final boolean canRegister = aggregate.canRegisterSeason(seasonId);
		if (!canRegister) {
			throw new IllegalArgumentException("teamSeason.isRegistered");
		}

		final TeamSeason entity = new TeamSeason(teamSeasonId, dto);
		teamSeasonRepository.add(entity);
		return teamSeasonId;
	}

	@CommandHandler
	protected void handle(EditTeamSeasonCommand command) {
		// Update the team value object
		final TeamId identifier = command.getTeamId();
		final Team aggregate = repository.load(identifier);
		aggregate.updateSeason(command.getTeamSeasonDTO());

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
		final Team aggregate = repository.load(identifier);
		aggregate.createPassword(identifier, command.getTeamDTO());
	}

	@CommandHandler
	protected void handle(UpdateTeamPasswordCommand command) {
		final TeamId identifier = command.getTeamId();
		final Team aggregate = repository.load(identifier);
		aggregate.updatePassword(identifier, command.getTeamDTO());
	}

}
