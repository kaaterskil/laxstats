package laxstats.domain.teams;

import java.util.HashSet;
import java.util.Set;

import laxstats.api.teams.TeamCreatedEvent;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamId;
import laxstats.domain.Gender;
import laxstats.query.events.TeamEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.joda.time.LocalDateTime;

public class Team extends AbstractAnnotatedAggregateRoot<TeamId> {
	
	@AggregateIdentifier
	private String id;
	
	private String name;
	private Gender gender;
	private String homeSiteId;
	private String encryptedPassword;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
	private Set<TeamEvent> teamEvents = new HashSet<TeamEvent>();
	private Set<TeamSeason> teamSeasons = new HashSet<TeamSeason>();
	private Set<TeamAffiliation> teamAffiliations = new HashSet<TeamAffiliation>();
	
	protected Team() {}
	
	public Team(TeamId teamId, TeamDTO teamDTO) {
		apply(new TeamCreatedEvent(teamId, teamDTO));
	}
}
