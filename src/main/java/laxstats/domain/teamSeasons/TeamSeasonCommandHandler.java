package laxstats.domain.teamSeasons;

import laxstats.api.players.PlayerDTO;
import laxstats.api.teamSeasons.DeleteTeamSeason;
import laxstats.api.teamSeasons.DropPlayer;
import laxstats.api.teamSeasons.EditPlayer;
import laxstats.api.teamSeasons.RegisterPlayer;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.domain.players.Player;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@code TeamSeasonCommandHandler} manages command for the team season aggregate.
 */
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

   /**
    * Deletes an existing team season matching the aggregate identifier contained in the given
    * command.
    *
    * @param command
    */
   @CommandHandler
   protected void handle(DeleteTeamSeason command) {
      final TeamSeasonId identifier = command.getTeamSeasonId();
      final TeamSeason entity = repository.load(identifier);
      entity.delete(command);
   }

   /*---------- Roster commands ----------*/

   /**
    * Adds and persists a player to a existing team season with information contained in the given
    * command. Throws an IllegalArgumentException if the given player cannot be added to the team
    * roster.
    *
    * @param command
    */
   @CommandHandler
   protected void handle(RegisterPlayer command) {
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

   /**
    * Updates and persists a player on a team roster with information contained in the given
    * command.
    *
    * @param command
    */
   @CommandHandler
   protected void handle(EditPlayer command) {
      final TeamSeasonId identifier = command.getTeamSeasonId();
      final TeamSeason teamSeason = repository.load(identifier);
      try {
         // Test and update the team value object
         teamSeason.updatePlayer(command.getPlayerDTO());

         // Update the aggregate
         final PlayerDTO dto = command.getPlayerDTO();
         final Player entity = playerRepository.load(dto.getId());
         entity.update(dto);
      }
      catch (final Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * Removes a player matching the aggregate identifier contained in the given command from the
    * team roster.
    *
    * @param command
    */
   @CommandHandler
   protected void handle(DropPlayer command) {
      final TeamSeasonId identifier = command.getTeamSeasonId();
      final TeamSeason teamSeason = repository.load(identifier);
      try {
         // Test and remove the team value object
         teamSeason.dropPlayer(command.getPlayerId());

         // Delete the aggregate
         final Player entity = playerRepository.load(command.getPlayerId());
         entity.delete();
      }
      catch (final Exception e) {
         // TODO: handle exception
      }
   }
}
