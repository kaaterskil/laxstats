package laxstats.domain.games;

import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.AttendeeNotRegisteredException;
import laxstats.api.games.CreateEventCommand;
import laxstats.api.games.DeleteAttendeeCommand;
import laxstats.api.games.DeleteClearCommand;
import laxstats.api.games.DeleteEventCommand;
import laxstats.api.games.DeleteFaceOffCommand;
import laxstats.api.games.DeleteGoalCommand;
import laxstats.api.games.DeleteGroundBallCommand;
import laxstats.api.games.DeletePenaltyCommand;
import laxstats.api.games.DeleteShotCommand;
import laxstats.api.games.GameDTO;
import laxstats.api.games.GameId;
import laxstats.api.games.RecordClearCommand;
import laxstats.api.games.RecordFaceoffCommand;
import laxstats.api.games.RecordGoalCommand;
import laxstats.api.games.RecordGroundBallCommand;
import laxstats.api.games.RecordPenaltyCommand;
import laxstats.api.games.RecordShotCommand;
import laxstats.api.games.RegisterAttendeeCommand;
import laxstats.api.games.UnavailablePlayerException;
import laxstats.api.games.UpdateAttendeeCommand;
import laxstats.api.games.UpdateClearCommand;
import laxstats.api.games.UpdateEventCommand;
import laxstats.api.games.UpdateFaceOffCommand;
import laxstats.api.games.UpdateGoalCommand;
import laxstats.api.games.UpdateGroundBallCommand;
import laxstats.api.games.UpdatePenaltyCommand;
import laxstats.api.games.UpdateShotCommand;
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

@Component
public class EventCommandHandler {
   private static Logger logger = LoggerFactory.getLogger(EventCommandHandler.class);
   private static String PACKAGE_NAME = EventCommandHandler.class.getPackage().getName();

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

   @CommandHandler
   public GameId handle(CreateEventCommand command) {
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

   @CommandHandler
   public void handle(UpdateEventCommand command) {
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
         } else {
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
         } else {
            logger.debug(proc + "70");
            teamTwo.scheduleEvent(dto);
         }
      }

      final Game game = repository.load(identifier);
      game.update(identifier, dto);
      logger.debug("Leaving: " + proc + "80");
   }

   @CommandHandler
   public void handle(DeleteEventCommand command) {
      final GameId gameId = command.getEventId();
      final Game game = repository.load(gameId);
      game.delete(gameId);
   }

   /* --------- Attendee commands ---------- */

   @CommandHandler
   public void handle(RegisterAttendeeCommand command) {
      final GameId identifier = command.getEventId();
      final Game aggregate = repository.load(identifier);
      final AttendeeDTO dto = command.getAttendeeDTO();

      // Test if attendee is a player and if player is not inactive or
      // injured.
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

   @CommandHandler
   public void handle(UpdateAttendeeCommand command) {
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

   @CommandHandler
   public void handle(DeleteAttendeeCommand command) {
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

   /* --------- Clear commands ---------- */

   @CommandHandler
   public void handle(RecordClearCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordClear(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateClearCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateClear(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteClearCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteClear(command);
   }

   /* --------- FaceOff commands ---------- */

   @CommandHandler
   public void handle(RecordFaceoffCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordFaceOff(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateFaceOffCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateFaceOff(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteFaceOffCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteFaceOff(command);
   }

   /* --------- Goal commands ---------- */

   @CommandHandler
   public void handle(RecordGoalCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordGoal(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateGoalCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateGoal(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteGoalCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteGoal(command);
   }

   /* --------- Ground Ball commands ---------- */

   @CommandHandler
   public void handle(RecordGroundBallCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordGroundBall(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateGroundBallCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateGroundBall(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteGroundBallCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteGroundBall(command);
   }

   /*---------- Penalty commands ----------*/

   @CommandHandler
   public void handle(RecordPenaltyCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordPenalty(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdatePenaltyCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updatePenalty(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeletePenaltyCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deletePenalty(command);
   }

   /* --------- Shot commands ---------- */

   @CommandHandler
   public void handle(RecordShotCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.recordShot(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateShotCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.updateShot(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteShotCommand command) {
      final GameId identifier = command.getEventId();
      final Game game = repository.load(identifier);
      game.deleteShot(command);
   }
}
