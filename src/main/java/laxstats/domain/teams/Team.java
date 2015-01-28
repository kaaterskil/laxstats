package laxstats.domain.teams;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teams.TeamCreatedEvent;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamDeletedEvent;
import laxstats.api.teams.TeamGender;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.TeamPasswordCreatedEvent;
import laxstats.api.teams.TeamPasswordUpdatedEvent;
import laxstats.api.teams.TeamSeasonEditedEvent;
import laxstats.api.teams.TeamSeasonRegisteredEvent;
import laxstats.api.teams.TeamUpdatedEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Team extends AbstractAnnotatedAggregateRoot<TeamId> {
	private static final long serialVersionUID = 88398210263680003L;

	@AggregateIdentifier
	private TeamId teamId;

	private String name;
	private TeamGender gender;
	private String homeSiteId;
	private String encodedPassword;
	private final List<TeamSeasonInfo> seasons = new ArrayList<>();

	public Team(TeamId teamId, TeamDTO teamDTO) {
		apply(new TeamCreatedEvent(teamId, teamDTO));
	}

	protected Team() {
	}

	/*---------- Methods ----------*/

	public void update(TeamId teamId, TeamDTO teamDTO) {
		apply(new TeamUpdatedEvent(teamId, teamDTO));
	}

	public void delete(TeamId teamId) {
		apply(new TeamDeletedEvent(teamId));
	}

	public void createPassword(TeamId teamId, TeamDTO teamDTO) {
		apply(new TeamPasswordCreatedEvent(teamId, teamDTO));
	}

	public void updatePassword(TeamId teamId, TeamDTO teamDTO) {
		apply(new TeamPasswordUpdatedEvent(teamId, teamDTO));
	}

	public void registerSeason(TeamSeasonDTO dto) {
		apply(new TeamSeasonRegisteredEvent(teamId, dto));
	}

	public boolean canRegisterSeason(String seasonId) {
		for (final TeamSeasonInfo each : seasons) {
			if (each.getSeasonId().equals(seasonId)) {
				return false;
			}
		}
		return true;
	}

	public void updateSeason(TeamSeasonDTO dto) {
		apply(new TeamSeasonEditedEvent(teamId, dto));
	}

	/*---------- Event handlers ----------*/

	@EventSourcingHandler
	protected void handle(TeamCreatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		teamId = event.getTeamId();
		name = dto.getName();
		gender = dto.getGender();
		if (dto.getHomeSite() != null) {
			homeSiteId = dto.getHomeSite().toString();
		}
	}

	@EventSourcingHandler
	protected void handle(TeamUpdatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		name = dto.getName();
		gender = dto.getGender();
		if (dto.getHomeSite() != null) {
			homeSiteId = dto.getHomeSite().toString();
		}
	}

	@EventSourcingHandler
	protected void handle(TeamDeletedEvent event) {
		markDeleted();
	}

	@EventSourcingHandler
	protected void handle(TeamSeasonRegisteredEvent event) {
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();

		final TeamSeasonInfo vo = new TeamSeasonInfo(dto.getTeamSeasonId()
				.toString(), dto.getTeam().getId(), dto.getSeason().getId(),
				dto.getStartsOn(), dto.getEndsOn(), dto.getStatus());
		seasons.add(vo);
	}

	@EventSourcingHandler
	protected void handle(TeamSeasonEditedEvent event) {
		final TeamSeasonDTO dto = event.getTeamSeasonDTO();
		final String id = dto.getTeamSeasonId().toString();

		for (final TeamSeasonInfo each : seasons) {
			if (each.getId().equals(id)) {
				seasons.remove(each);
			}
		}
		final TeamSeasonInfo vo = new TeamSeasonInfo(dto.getTeamSeasonId()
				.toString(), dto.getTeam().getId(), dto.getSeason().getId(),
				dto.getStartsOn(), dto.getEndsOn(), dto.getStatus());
		seasons.add(vo);
	}

	@EventSourcingHandler
	protected void handle(TeamPasswordCreatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		encodedPassword = dto.getEncodedPassword();
	}

	@EventSourcingHandler
	protected void handle(TeamPasswordUpdatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		encodedPassword = dto.getEncodedPassword();
	}

	/*---------- Getters ----------*/

	@Override
	public TeamId getIdentifier() {
		return teamId;
	}

	public String getName() {
		return name;
	}

	public TeamGender getGender() {
		return gender;
	}

	public String getHomeSiteId() {
		return homeSiteId;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public List<TeamSeasonInfo> getSeasons() {
		return seasons;
	}

}
