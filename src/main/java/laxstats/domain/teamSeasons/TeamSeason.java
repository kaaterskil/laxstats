package laxstats.domain.teamSeasons;

import laxstats.api.teamSeasons.DeleteTeamSeasonCommand;
import laxstats.api.teamSeasons.TeamSeasonCreatedEvent;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonDeletedEvent;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamSeasonUpdatedEvent;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.api.teamSeasons.UpdateTeamSeasonCommand;

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

	public TeamSeason(TeamSeasonId teamSeasonId, TeamSeasonDTO teamSeasonDTO) {
		apply(new TeamSeasonCreatedEvent(teamSeasonId, teamSeasonDTO));
	}

	protected TeamSeason() {
	}

	// ---------- Methods ---------- //

	public void update(UpdateTeamSeasonCommand command) {
		apply(new TeamSeasonUpdatedEvent(command.getTeamSeasonId(),
				command.getTeamSeasonDTO()));
	}

	public void delete(DeleteTeamSeasonCommand command) {
		apply(new TeamSeasonDeletedEvent(command.getTeamSeasonId()));
	}

	// ---------- Event handlers ---------- //

	@EventSourcingHandler
	protected void handle(TeamSeasonCreatedEvent event) {
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();
		id = event.getTeamSeasonId();
		teamId = dto.getTeam().getId();
		seasonId = dto.getSeason().getId();
		status = dto.getStatus();
		startsOn = dto.getStartsOn();
		endsOn = dto.getEndsOnd();
	}

	@EventSourcingHandler
	protected void handle(TeamSeasonUpdatedEvent event) {
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();
		teamId = dto.getTeam().getId();
		seasonId = dto.getSeason().getId();
		status = dto.getStatus();
		startsOn = dto.getStartsOn();
		endsOn = dto.getEndsOnd();
	}

	@EventSourcingHandler
	protected void handle(TeamSeasonDeletedEvent event) {
		markDeleted();
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
}
