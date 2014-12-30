package laxstats.domain.events;

import laxstats.api.events.CreateEventCommand;
import laxstats.api.events.DeleteAttendeeCommand;
import laxstats.api.events.DeleteEventCommand;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventId;
import laxstats.api.events.RegisterAttendeeCommand;
import laxstats.api.events.UpdateAttendeeCommand;
import laxstats.api.events.UpdateEventCommand;
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
}
