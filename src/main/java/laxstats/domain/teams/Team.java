package laxstats.domain.teams;

import java.util.HashSet;
import java.util.Set;

import laxstats.domain.Gender;
import laxstats.query.events.TeamEvent;

import org.joda.time.LocalDateTime;

public class Team {
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
}
