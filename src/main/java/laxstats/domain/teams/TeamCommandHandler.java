package laxstats.domain.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teams.CreateTeam;
import laxstats.api.teams.CreateTeamPassword;
import laxstats.api.teams.DeleteTeam;
import laxstats.api.teams.EditTeamSeason;
import laxstats.api.teams.RegisterTeamSeason;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.UpdateTeam;
import laxstats.api.teams.UpdateTeamPassword;
import laxstats.domain.teamSeasons.TeamSeason;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@code TeamCommandHandler} manages commands for the team aggregate.
 */
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
   public void setTeamSeasonRepository(Repository<TeamSeason> teamSeasonRepository) {
      this.teamSeasonRepository = teamSeasonRepository;
   }

   /*---------- Team commands ----------*/

   /**
    * Creates and persists a new team with information contained in the payload of the given
    * command.
    *
    * @param command
    * @return
    */
   @CommandHandler
   protected TeamId handle(CreateTeam command) {
      final TeamId identifier = command.getTeamId();
      final Team aggregate = new Team(identifier, command.getTeamDTO());
      repository.add(aggregate);
      return identifier;
   }

   /**
    * Updates and persists an existing team with information contained in the payload of the given
    * command.
    *
    * @param command
    */
   @CommandHandler
   protected void handle(UpdateTeam command) {
      final TeamId identifier = command.getTeamId();
      final Team aggregate = repository.load(identifier);
      aggregate.update(identifier, command.getTeamDTO());
   }

   /**
    * Deletes an existing team matching the aggregate identifier contained in the payload of the
    * given command.
    *
    * @param command
    */
   @CommandHandler
   protected void handle(DeleteTeam command) {
      final TeamId identifier = command.getTeamId();
      final Team aggregate = repository.load(identifier);
      aggregate.delete(identifier);
   }

   /*---------- Team Season commands ----------*/

   /**
    * Registers a team season with an existing team based on information contained in the payload of
    * the given command. An exception is thrown if the aggregate disallows the team season.
    *
    * Note that the elements in the team season collection are value objects.
    *
    * @param command
    * @return
    */
   @CommandHandler
   protected TeamSeasonId handle(RegisterTeamSeason command) throws IllegalArgumentException {
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

   /**
    * Updates a season within a team from information contained in the payload of the given command.
    * Note that the elements in the team season collection are value objects that are replaced with
    * the updated information.
    *
    * @param command
    */
   @CommandHandler
   protected void handle(EditTeamSeason command) {
      // Update the team value object
      final TeamId identifier = command.getTeamId();
      final Team aggregate = repository.load(identifier);
      aggregate.updateSeason(command.getTeamSeasonDTO());

      // Update the aggregate
      final TeamSeasonDTO dto = command.getTeamSeasonDTO();
      final TeamSeason teamSeason = teamSeasonRepository.load(dto.getTeamSeasonId());
      teamSeason.update(dto.getTeamSeasonId(), dto);
   }

   /*---------- Password commands ----------*/

   /**
    * Creates a new password for an existing team from information contained in the payload of the
    * given command.
    * 
    * @param command
    */
   @CommandHandler
   protected void handle(CreateTeamPassword command) {
      final TeamId identifier = command.getTeamId();
      final Team aggregate = repository.load(identifier);
      aggregate.createPassword(identifier, command.getTeamDTO());
   }

   /**
    * Updates an existing team password from information contained in the payload of the given
    * command.
    * 
    * @param command
    */
   @CommandHandler
   protected void handle(UpdateTeamPassword command) {
      final TeamId identifier = command.getTeamId();
      final Team aggregate = repository.load(identifier);
      aggregate.updatePassword(identifier, command.getTeamDTO());
   }

}
