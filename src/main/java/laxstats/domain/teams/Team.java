package laxstats.domain.teams;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.people.Gender;
import laxstats.api.teams.SeasonRegisteredEvent;
import laxstats.api.teams.TeamCreatedEvent;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamDeletedEvent;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.TeamPasswordCreatedEvent;
import laxstats.api.teams.TeamPasswordUpdatedEvent;
import laxstats.api.teams.TeamUpdatedEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Team extends AbstractAnnotatedAggregateRoot<TeamId> {
	private static final long serialVersionUID = 88398210263680003L;

	@AggregateIdentifier
	private TeamId teamId;

	private String name;
	private Gender gender;
	private String homeSiteId;
	private String encodedPassword;
	private final List<TeamSeasonInfo> seasons = new ArrayList<>();

	public Team(TeamId teamId, TeamDTO teamDTO) {
		apply(new TeamCreatedEvent(teamId, teamDTO));
	}

	protected Team() {
	}

	// ---------- Methods ----------//

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

	public void registerSeason(TeamSeasonInfo teamSeason) {
		if (!canTeamRegisterSeason(teamSeason)) {
			throw new IllegalArgumentException("season already registered");
		}
		apply(new SeasonRegisteredEvent(teamId, teamSeason));
	}

	private boolean canTeamRegisterSeason(TeamSeasonInfo teamSeason) {
		return !seasons.contains(teamSeason);
	}

	// ---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(TeamCreatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		teamId = event.getTeamId();
		name = dto.getName();
		gender = dto.getGender();
		homeSiteId = dto.getHomeSite().toString();
	}

	@EventSourcingHandler
	protected void handle(TeamUpdatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();
		name = dto.getName();
		gender = dto.getGender();
		homeSiteId = dto.getHomeSite().toString();
	}

	@EventSourcingHandler
	protected void handle(TeamDeletedEvent event) {
		markDeleted();
	}

	@EventSourcingHandler
	protected void handle(SeasonRegisteredEvent event) {
		final TeamSeasonInfo season = event.getSeason();
		seasons.add(season);
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

	// ---------- Getters ----------//

	@Override
	public TeamId getIdentifier() {
		return teamId;
	}

	public String getName() {
		return name;
	}

	public Gender getGender() {
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
