package laxstats.domain.games;

import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.AttendeeNotRegisteredException;
import laxstats.api.games.CreateGame;
import laxstats.api.games.DeleteAttendee;
import laxstats.api.games.DeleteClear;
import laxstats.api.games.DeleteFaceOff;
import laxstats.api.games.DeleteGame;
import laxstats.api.games.DeleteGoal;
import laxstats.api.games.DeleteGroundBall;
import laxstats.api.games.DeletePenalty;
import laxstats.api.games.DeleteShot;
import laxstats.api.games.GameDTO;
import laxstats.api.games.GameId;
import laxstats.api.games.RecordClear;
import laxstats.api.games.RecordFaceoff;
import laxstats.api.games.RecordGoal;
import laxstats.api.games.RecordGroundBall;
import laxstats.api.games.RecordPenalty;
import laxstats.api.games.RecordShot;
import laxstats.api.games.RegisterAttendee;
import laxstats.api.games.UnavailablePlayerException;
import laxstats.api.games.UpdateAttendee;
import laxstats.api.games.UpdateClear;
import laxstats.api.games.UpdateFaceOff;
import laxstats.api.games.UpdateGame;
import laxstats.api.games.UpdateGoal;
import laxstats.api.games.UpdateGroundBall;
import laxstats.api.games.UpdatePenalty;
import laxstats.api.games.UpdateShot;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Role;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.domain.teamSeasons.TeamSeason;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@code GameCommandHandler} manages commands for the game aggregate.
 */
@Component
public class GameCommandHandler {
   private static Logger logger = LoggerFactory.getLogger(GameCommandHandler.class);
   private static String PACKAGE_NAME = GameCommandHandler.class.getPackage().getName();

   private Repository<Game> repository;
   private Repository<TeamSeason> teamSeasonRepository;

   @Autowired
   @Qualifier("eventRepository")
   public void setRepository(Repository<Game> repository) {
      this.repository = repository;
   }

   @Autowired
   @Qualifier("teamSeasonRepository")
   public void setTeamSeasonRepository(Repository<TeamSeason> teamSeasonRepository) {
      this.teamSeasonRepository = teamSeasonRepository;
   }

   /**
    * Creates and persists a new game from information contained in the given command.
    *
    * @param command
    * @return
    */
   @CommandHandler
   public GameId handle(CreateGame command) {
      final String proc = PACKAGE_NAME + ".createGame.";
      final GameId identifier = command.getEventId();
      final GameDTO dto = command.getEventDTO();

      logger.debug("Entering: " + proc + "10");
      if (dto.getTeamOne() != null) {
         logger.debug(proc + "20");

         final TeamSeasonId teamOneId = new TeamSeasonId(dto.getTeamOne().getId());
         final TeamSeason teamOne = teamSeasonRepository.load(teamOneId);
         teamOne.scheduleEvent(dto);
      }

      if (dto.getTeamTwo() != null) {
         logger.debug(proc + "30");

         final TeamSeasonId teamTwoId = new TeamSeasonId(dto.getTeamTwo().getId());
         final TeamSeason teamTwo = teamSeasonRepository.load(teamTwoId);
         teamTwo.scheduleEvent(dto);
      }

      final Game entity = new Game(identifier, command.getEventDTO());
      repository.add(entity);
      logger.debug("Leaving: " + proc + "40");

      return identifier;
   }

   /**
    * Updates and persists an existing game with information contained in the given command. Teams
    * may be added or updated..
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateGame command) {
      final String proc = PACKAGE_NAME + ".updateGame.";
      final GameId identifier = command.getEventId();
      final GameDTO dto = command.getEventDTO();

      logger.debug("Entering: " + proc + "10");
      if (dto.getTeamOne() != null) {
         logger.debug(proc + "20");

         final TeamSeasonId teamOneId = new TeamSeasonId(dto.getTeamOne().getId());
         final TeamSeason teamOne = teamSeasonRepository.load(teamOneId);
         if (teamOne.alreadyScheduled(dto)) {
            logger.debug(proc + "30");
            teamOne.updateEvent(dto);
         }
         else {
            logger.debug(proc + "40");
            teamOne.scheduleEvent(dto);
         }
      }

      if (dto.getTeamTwo() != null) {
         logger.debug(proc + "50");

         final TeamSeasonId teamTwoId = new TeamSeasonId(dto.getTeamTwo().getId());
         final TeamSeason teamTwo = teamSeasonRepository.load(teamTwoId);
         if (teamTwo.alreadyScheduled(dto)) {
            logger.debug(proc + "60");
            teamTwo.updateEvent(dto);
         }
         else {
            logger.debug(proc + "70");
            teamTwo.scheduleEvent(dto);
         }
      }

      final Game game = repository.load(identifier);
      game.update(identifier, dto);
      logger.debug("Leaving: " + proc + "80");
   }

   /**
    * Deletes the game matching the aggregate identifier contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteGame command) {
      final GameId gameId = command.getEventId();
      final Game game = repository.load(gameId);
      game.delete(gameId);
   }

   /**
    * Creates and persists a new game attendee from information contained in the given command. An
    * UnavailablePlayerException is thrown if the given player is not an active player. An
    * AttendeeNotRegisteredException is thrown if the game attendee is not registered.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RegisterAttendee command) {
      final GameId identifier = command.getEventId();
      final Game aggregate = repository.load(identifier);
      final AttendeeDTO dto = command.getAttendeeDTO();

      // Test if attendee is a player and if player is not inactive or injured.
      if (dto.getPlayer() != null && dto.getRole().equals(Role.ATHLETE)) {
         final PlayerStatus status = dto.getPlayer().getStatus();
         if (!status.equals(PlayerStatus.ACTIVE) && !status.equals(PlayerStatus.TRYOUT)) {
            throw new UnavailablePlayerException();
         }
      }

      // Test if attendee is already registered
      final String playerId = dto.getPlayer().getId();
      final boolean isRegistered = aggregate.isRegisteredAttendee(playerId);
      if (isRegistered) {
         throw new IllegalArgumentException("attendee.isRegistered");
      }

      aggregate.registerAttendee(dto);
   }

   /**
    * Updates and persists an existing game attendee with information contained in the given
    * command. An AttendeeNotRegisteredException is thrown if the game attendee is not registered.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateAttendee command) {
      final GameId identifier = command.getEventId();
      final Game aggregate = repository.load(identifier);
      final AttendeeDTO dto = command.getAttendeeDTO();

      // Test if attendee is registered
      final String playerId = dto.getPlayer().getId();
      final boolean isRegistered = aggregate.isRegisteredAttendee(playerId);
      if (!isRegistered) {
         final String msg = dto.getName() + " is not registered for this event";
         throw new AttendeeNotRegisteredException(msg);
      }

      aggregate.updateAttendee(command.getAttendeeDTO());
   }

   /**
    * Deletes the game attendee matching the unique identifier contained in the given command. An
    * AttendeeNotRegisteredException is thrown if the game attendee is not registered.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteAttendee command) {
      final GameId identifier = command.getEventId();
      final Game aggregate = repository.load(identifier);
      final String attendeeId = command.getAttendeeId();

      boolean registered = false;
      String attendeeName = null;
      for (final Attendee each : aggregate.getAttendees().values()) {
         if (each.getId().equals(attendeeId)) {
            registered = true;
            attendeeName = each.getName();
            break;
         }
      }
      if (!registered) {
         final String msg = attendeeName + " is not registered for this event";
         throw new AttendeeNotRegisteredException(msg);
      }

      aggregate.deleteAttendee(command.getAttendeeId());
   }

   /**
    * Creates and persists a new clear from information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RecordClear command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordClear(command.getPlayDTO());
   }

   /**
    * Updates and persists an existing clear with information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateClear command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateClear(command.getPlayDTO());
   }

   /**
    * Deletes the clear matching the unique identifier contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteClear command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteClear(command);
   }

   /**
    * Creates and persists a new face-off from information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RecordFaceoff command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordFaceOff(command.getPlayDTO());
   }

   /**
    * Updates and persists an existing face-off with information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateFaceOff command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateFaceOff(command.getPlayDTO());
   }

   /**
    * Deletes the face-off matching the unique identifier contained in the given commnd.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteFaceOff command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteFaceOff(command);
   }

   /**
    * Creates and persists a new goal from information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RecordGoal command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordGoal(command.getPlayDTO());
   }

   /**
    * Updatesand persists an existing goal with information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateGoal command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateGoal(command.getPlayDTO());
   }

   /**
    * Deletes the goal matching the unique identifier contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteGoal command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteGoal(command);
   }

   /**
    * Creates and persists a new gorund ball from information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RecordGroundBall command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordGroundBall(command.getPlayDTO());
   }

   /**
    * Updtes and persists an existing ground ball with information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateGroundBall command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateGroundBall(command.getPlayDTO());
   }

   /**
    * Deletes the ground ball matching the unique identifier contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteGroundBall command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteGroundBall(command);
   }

   /**
    * Creates an persists a new penalty from information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RecordPenalty command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordPenalty(command.getPlayDTO());
   }

   /**
    * Updates and persists an existing penalty with information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdatePenalty command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updatePenalty(command.getPlayDTO());
   }

   /**
    * Deletes the penalty matching the unique identifier contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeletePenalty command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deletePenalty(command);
   }

   /**
    * Creates a new shot from information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RecordShot command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordShot(command.getPlayDTO());
   }

   /**
    * Updates and persists an existing shot with information contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateShot command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateShot(command.getPlayDTO());
   }

   /**
    * Deletes the shot matching the unique identifier contained in the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteShot command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteShot(command);
   }
}
