package laxstats.domain.teamSeasons;

import laxstats.api.players.PlayerDTO;
import laxstats.api.teamSeasons.DeleteTeamSeasonCommand;
import laxstats.api.teamSeasons.DropPlayerCommand;
import laxstats.api.teamSeasons.EditPlayerCommand;
import laxstats.api.teamSeasons.RegisterPlayerCommand;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.domain.players.Player;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TeamSeasonCommandHandler {
	private Repository<TeamSeason> repository;
	private Repository<Player> playerRepository;

	@Autowired
	@Qualifier("teamSeasonRepository")
	public void setTeamSeasonRepository(Repository<TeamSeason> repository) {
		this.repository = repository;
	}

	@Autowired
	@Qualifier("playerRepository")
	public void setPlayerRepository(Repository<Player> playerRepository) {
		this.playerRepository = playerRepository;
	}

	/*---------- TeamSeason commands ----------*/

	@CommandHandler
	protected void handle(DeleteTeamSeasonCommand command) {
		final TeamSeasonId identifier = command.getTeamSeasonId();
		final TeamSeason entity = repository.load(identifier);
		entity.delete(command);
	}

	/*---------- Roster commands ----------*/

	@CommandHandler
	protected void handle(RegisterPlayerCommand command) {
		final TeamSeasonId identifier = command.getTeamSeasonId();
		final TeamSeason teamSeason = repository.load(identifier);

		final PlayerDTO dto = command.getPlayerDTO();
		final String playerId = dto.getId().toString();

		final boolean canRegister = teamSeason.canRegisterPlayer(playerId);
		if (!canRegister) {
			throw new IllegalArgumentException("player.isRegistered");
		}

		final Player entity = new Player(dto.getId(), dto);
		playerRepository.add(entity);
	}

	@CommandHandler
	protected void handle(EditPlayerCommand command) {
		final TeamSeasonId identifier = command.getTeamSeasonId();
		final TeamSeason teamSeason = repository.load(identifier);
		try {
			// Test and update the team value object
			teamSeason.updatePlayer(command.getPlayerDTO());

			// Update the aggregate
			final PlayerDTO dto = command.getPlayerDTO();
			final Player entity = playerRepository.load(dto.getId());
			entity.update(dto);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@CommandHandler
	protected void handle(DropPlayerCommand command) {
		final TeamSeasonId identifier = command.getTeamSeasonId();
		final TeamSeason teamSeason = repository.load(identifier);
		try {
			// Test and remove the team value object
			teamSeason.dropPlayer(command.getPlayerId());

			// Delete the aggregate
			final Player entity = playerRepository.load(command.getPlayerId());
			entity.delete();
		} catch (final Exception e) {
			// TODO: handle exception
		}
	}
}
