package laxstats.domain.teams;

import java.util.HashSet;
import java.util.Set;

import laxstats.api.people.Gender;
import laxstats.api.teams.TeamCreatedEvent;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamDeletedEvent;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.TeamUpdatedEvent;
import laxstats.query.events.TeamEvent;

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
	private final Set<TeamEvent> teamEvents = new HashSet<TeamEvent>();
	private final Set<TeamSeason> teamSeasons = new HashSet<TeamSeason>();
	private final Set<TeamAffiliation> teamAffiliations = new HashSet<TeamAffiliation>();

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

	// ---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(TeamCreatedEvent event) {
		final TeamDTO dto = event.getTeamDTO();

		teamId = event.getTeamId();
		name = dto.getName();
		gender = dto.getGender();
		homeSiteId = dto.getHomeSite().toString();
		encodedPassword = dto.getEncodedPassword();
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

	// ---------- Getters ----------//

	@Override
	public TeamId getIdentifier() {
		return teamId;
	}

	public void setTeamId(TeamId teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getHomeSiteId() {
		return homeSiteId;
	}

	public void setHomeSiteId(String homeSiteId) {
		this.homeSiteId = homeSiteId;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	public Set<TeamEvent> getTeamEvents() {
		return teamEvents;
	}

	public Set<TeamSeason> getTeamSeasons() {
		return teamSeasons;
	}

	public Set<TeamAffiliation> getTeamAffiliations() {
		return teamAffiliations;
	}

}
