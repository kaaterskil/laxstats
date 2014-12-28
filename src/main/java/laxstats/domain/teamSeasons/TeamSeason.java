package laxstats.domain.teamSeasons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laxstats.api.events.EventDTO;
import laxstats.api.teamSeasons.DeleteTeamSeasonCommand;
import laxstats.api.teamSeasons.EventAlreadyScheduledException;
import laxstats.api.teamSeasons.EventRevisedEvent;
import laxstats.api.teamSeasons.EventScheduleConflictException;
import laxstats.api.teamSeasons.EventScheduledEvent;
import laxstats.api.teamSeasons.PlayerRegisteredEvent;
import laxstats.api.teamSeasons.TeamSeasonCreatedEvent;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonDeletedEvent;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamSeasonUpdatedEvent;
import laxstats.api.teamSeasons.TeamStatus;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDate;

public class TeamSeason extends AbstractAnnotatedAggregateRoot<TeamSeasonId> {
	private static final long serialVersionUID = 687219884714746674L;

	@AggregateIdentifier
	private TeamSeasonId id;

	private String teamId;
	private String seasonId;
	private TeamStatus status;
	private LocalDate startsOn;
	private LocalDate endsOn;
	private final List<PlayerInfo> roster = new ArrayList<>();
	private final List<EventInfo> events = new ArrayList<>();

	public TeamSeason(TeamSeasonId teamSeasonId, TeamSeasonDTO teamSeasonDTO) {
		apply(new TeamSeasonCreatedEvent(teamSeasonId, teamSeasonDTO));
	}

	protected TeamSeason() {
	}

	// ---------- Methods ---------- //

	public void update(TeamSeasonId identifier, TeamSeasonDTO dto) {
		apply(new TeamSeasonUpdatedEvent(identifier, dto));
	}

	public void delete(DeleteTeamSeasonCommand command) {
		apply(new TeamSeasonDeletedEvent(command.getTeamSeasonId()));
	}

	public void registerPlayer(PlayerInfo player) {
		if (!canRegisterPlayer(player)) {
			throw new IllegalArgumentException("player already registered");
		}
		apply(new PlayerRegisteredEvent(id, player));
	}

	private boolean canRegisterPlayer(PlayerInfo player) {
		return !roster.contains(player);
	}

	public void scheduleEvent(EventDTO dto) {
		if (alreadyScheduled(dto)) {
			throw new EventAlreadyScheduledException();
		}
		if (scheduleConflicts(dto)) {
			throw new EventScheduleConflictException();
		}
		apply(new EventScheduledEvent(id, dto));
	}

	public boolean alreadyScheduled(EventDTO dto) {
		for (final EventInfo event : events) {
			if (event.getEventId().equals(dto.getId())) {
				return true;
			}
		}
		return false;
	}

	private boolean scheduleConflicts(EventDTO dto) {
		for (final EventInfo event : events) {
			if (!event.getEventId().equals(dto.getId())
					&& event.getStartsAt().equals(dto.getStartsAt())) {
				return true;
			}
		}
		return false;
	}

	public void updateEvent(EventDTO dto) {
		if (scheduleConflicts(dto)) {
			throw new EventScheduleConflictException();
		}
		apply(new EventRevisedEvent(id, dto));
	}

	// ---------- Event handlers ---------- //

	@EventSourcingHandler
	protected void handle(TeamSeasonCreatedEvent event) {
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();
		id = dto.getTeamSeasonId();
		teamId = dto.getTeam().getId();
		seasonId = dto.getSeason().getId();
		status = dto.getStatus();
		startsOn = dto.getStartsOn();
		endsOn = dto.getEndsOn();
	}

	@EventSourcingHandler
	protected void handle(TeamSeasonUpdatedEvent event) {
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();
		teamId = dto.getTeam().getId();
		seasonId = dto.getSeason().getId();
		status = dto.getStatus();
		startsOn = dto.getStartsOn();
		endsOn = dto.getEndsOn();
	}

	@EventSourcingHandler
	protected void handle(TeamSeasonDeletedEvent event) {
		markDeleted();
	}

	@EventSourcingHandler
	protected void handle(PlayerRegisteredEvent event) {
		final PlayerInfo player = event.getPlayer();
		roster.add(player);
	}

	@EventSourcingHandler
	protected void handle(EventScheduledEvent event) {
		final EventDTO dto = event.getEvent();
		final String siteId = dto.getSite() == null ? null : dto.getSite()
				.getId();
		final String teamOneId = dto.getTeamOne() == null ? null : dto
				.getTeamOne().getId();
		final String teamTwoId = dto.getTeamTwo() == null ? null : dto
				.getTeamTwo().getId();

		final EventInfo vo = new EventInfo(dto.getId(), siteId, teamOneId,
				teamTwoId, dto.getStartsAt());
		events.add(vo);
	}

	@EventSourcingHandler
	protected void handle(EventRevisedEvent event) {
		final EventDTO dto = event.getEventDTO();
		final String siteId = dto.getSite() == null ? null : dto.getSite()
				.getId();
		final String teamOneId = dto.getTeamOne() == null ? null : dto
				.getTeamOne().getId();
		final String teamTwoId = dto.getTeamTwo() == null ? null : dto
				.getTeamTwo().getId();

		final EventInfo vo = new EventInfo(dto.getId(), siteId, teamOneId,
				teamTwoId, dto.getStartsAt());

		final Iterator<EventInfo> iter = events.iterator();
		while (iter.hasNext()) {
			final EventInfo each = iter.next();
			if (each.getEventId().equals(dto.getId())) {
				iter.remove();
			}
		}
		events.add(vo);
	}

	// ---------- Getters ---------- //

	@Override
	public TeamSeasonId getIdentifier() {
		return id;
	}

	public String getTeamId() {
		return teamId;
	}

	public String getSeasonId() {
		return seasonId;
	}

	public TeamStatus getStatus() {
		return status;
	}

	public LocalDate getStartsOn() {
		return startsOn;
	}

	public LocalDate getEndsOn() {
		return endsOn;
	}

	public List<PlayerInfo> getRoster() {
		return roster;
	}

	public List<EventInfo> getEvents() {
		return events;
	}
}
