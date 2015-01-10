package laxstats.domain.events;

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
import laxstats.api.events.UpdateAttendeeCommand;
import laxstats.api.events.UpdateClearCommand;
import laxstats.api.events.UpdateEventCommand;
import laxstats.api.events.UpdateFaceOffCommand;
import laxstats.api.events.UpdateGoalCommand;
import laxstats.api.events.UpdateGroundBallCommand;
import laxstats.api.events.UpdatePenaltyCommand;
import laxstats.api.events.UpdateShotCommand;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.domain.teamSeasons.TeamSeason;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EventCommandHandler {
	private Repository<Event> repository;
	private Repository<TeamSeason> teamSeasonRepository;

	@Autowired
	@Qualifier("eventRepository")
	public void setRepository(Repository<Event> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public EventId handle(CreateEventCommand command) {
		final EventId identifier = command.getEventId();
		final EventDTO dto = command.getEventDTO();
		try {
			if (dto.getTeamOne() != null) {
				final TeamSeasonId teamOneId = new TeamSeasonId(dto
						.getTeamOne().getId());
				final TeamSeason teamOne = teamSeasonRepository.load(teamOneId);
				teamOne.scheduleEvent(dto);
			}
			if (dto.getTeamTwo() != null) {
				final TeamSeasonId teamTwoId = new TeamSeasonId(dto
						.getTeamTwo().getId());
				final TeamSeason teamTwo = teamSeasonRepository.load(teamTwoId);
				teamTwo.scheduleEvent(dto);
			}

			final Event entity = new Event(identifier, command.getEventDTO());
			repository.add(entity);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return identifier;
	}

	@CommandHandler
	public void handle(UpdateEventCommand command) {
		final EventId identifier = command.getEventId();
		final EventDTO dto = command.getEventDTO();
		try {
			if (dto.getTeamOne() != null) {
				final TeamSeasonId teamOneId = new TeamSeasonId(dto
						.getTeamOne().getId());
				final TeamSeason teamOne = teamSeasonRepository.load(teamOneId);
				if (teamOne.alreadyScheduled(dto)) {
					teamOne.updateEvent(dto);
				} else {
					teamOne.scheduleEvent(dto);
				}
			}
			if (dto.getTeamTwo() != null) {
				final TeamSeasonId teamTwoId = new TeamSeasonId(dto
						.getTeamTwo().getId());
				final TeamSeason teamTwo = teamSeasonRepository.load(teamTwoId);
				if (teamTwo.alreadyScheduled(dto)) {
					teamTwo.updateEvent(dto);
				} else {
					teamTwo.scheduleEvent(dto);
				}
			}
			final Event event = repository.load(identifier);
			event.update(identifier, dto);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		final Event event = repository.load(identifier);
		event.registerAttendee(command.getAttendeeDTO());
	}

	@CommandHandler
	public void handle(UpdateAttendeeCommand command) {
		final EventId identifier = command.getEventId();
		final Event event = repository.load(identifier);
		event.updateAttendee(command.getAttendeeDTO());
	}

	@CommandHandler
	public void handle(DeleteAttendeeCommand command) {
		final EventId identifier = command.getEventId();
		final Event event = repository.load(identifier);
		event.deleteAttendee(command.getAttendeeId());
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
