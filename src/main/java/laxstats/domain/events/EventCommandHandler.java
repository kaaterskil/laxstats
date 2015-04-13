package laxstats.domain.events;

import laxstats.api.events.AttendeeDTO;
import laxstats.api.events.AttendeeNotRegisteredException;
import laxstats.api.events.CreateEventCommand;
import laxstats.api.events.DeleteAttendeeCommand;
import laxstats.api.events.DeleteClearCommand;
import laxstats.api.events.DeleteEventCommand;
import laxstats.api.events.DeleteFaceOffCommand;
import laxstats.api.events.DeleteGoalCommand;
import laxstats.api.events.DeleteGroundBallCommand;
import laxstats.api.events.DeletePenaltyCommand;
import laxstats.api.events.DeleteShotCommand;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventId;
import laxstats.api.events.RecordClearCommand;
import laxstats.api.events.RecordFaceoffCommand;
import laxstats.api.events.RecordGoalCommand;
import laxstats.api.events.RecordGroundBallCommand;
import laxstats.api.events.RecordPenaltyCommand;
import laxstats.api.events.RecordShotCommand;
import laxstats.api.events.RegisterAttendeeCommand;
import laxstats.api.events.UnavailablePlayerException;
import laxstats.api.events.UpdateAttendeeCommand;
import laxstats.api.events.UpdateClearCommand;
import laxstats.api.events.UpdateEventCommand;
import laxstats.api.events.UpdateFaceOffCommand;
import laxstats.api.events.UpdateGoalCommand;
import laxstats.api.events.UpdateGroundBallCommand;
import laxstats.api.events.UpdatePenaltyCommand;
import laxstats.api.events.UpdateShotCommand;
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

   private Repository<Event> repository;
   private Repository<TeamSeason> teamSeasonRepository;

   @Autowired
   @Qualifier("eventRepository")
   public void setRepository(Repository<Event> repository) {
      this.repository = repository;
   }

   @Autowired
   @Qualifier("teamSeasonRepository")
   public void setTeamSeasonRepository(Repository<TeamSeason> teamSeasonRepository) {
      this.teamSeasonRepository = teamSeasonRepository;
   }

   @CommandHandler
   public EventId handle(CreateEventCommand command) {
      final String proc = PACKAGE_NAME + ".createGame.";
      final EventId identifier = command.getEventId();
      final EventDTO dto = command.getEventDTO();

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

      final Event entity = new Event(identifier, command.getEventDTO());
      repository.add(entity);
      logger.debug("Leaving: " + proc + "40");

      return identifier;
   }

   @CommandHandler
   public void handle(UpdateEventCommand command) {
      final String proc = PACKAGE_NAME + ".updateGame.";
      final EventId identifier = command.getEventId();
      final EventDTO dto = command.getEventDTO();

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

      final Event event = repository.load(identifier);
      event.update(identifier, dto);
      logger.debug("Leaving: " + proc + "80");
   }

   @CommandHandler
   public void handle(DeleteEventCommand command) {
      final EventId eventId = command.getEventId();
      final Event event = repository.load(eventId);
      event.delete(eventId);
   }

   /* --------- Attendee commands ---------- */

   @CommandHandler
   public void handle(RegisterAttendeeCommand command) {
      final EventId identifier = command.getEventId();
      final Event aggregate = repository.load(identifier);
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
      final EventId identifier = command.getEventId();
      final Event aggregate = repository.load(identifier);
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
      final EventId identifier = command.getEventId();
      final Event aggregate = repository.load(identifier);
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
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.recordClear(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateClearCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.updateClear(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteClearCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.deleteClear(command);
   }

   /* --------- FaceOff commands ---------- */

   @CommandHandler
   public void handle(RecordFaceoffCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.recordFaceOff(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateFaceOffCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.updateFaceOff(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteFaceOffCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.deleteFaceOff(command);
   }

   /* --------- Goal commands ---------- */

   @CommandHandler
   public void handle(RecordGoalCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.recordGoal(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateGoalCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.updateGoal(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteGoalCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.deleteGoal(command);
   }

   /* --------- Ground Ball commands ---------- */

   @CommandHandler
   public void handle(RecordGroundBallCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.recordGroundBall(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateGroundBallCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.updateGroundBall(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteGroundBallCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.deleteGroundBall(command);
   }

   /*---------- Penalty commands ----------*/

   @CommandHandler
   public void handle(RecordPenaltyCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.recordPenalty(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdatePenaltyCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.updatePenalty(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeletePenaltyCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.deletePenalty(command);
   }

   /* --------- Shot commands ---------- */

   @CommandHandler
   public void handle(RecordShotCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.recordShot(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(UpdateShotCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.updateShot(command.getPlayDTO());
   }

   @CommandHandler
   public void handle(DeleteShotCommand command) {
      final EventId identifier = command.getEventId();
      final Event event = repository.load(identifier);
      event.deleteShot(command);
   }
}
