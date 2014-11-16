package laxstats.domain.teams;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDateTime;

public class Affiliation {

	public enum Level {
		LEAGUE, DIVISION, CONFERENCE;
	}

	private String id;
	private String name;
	private Affiliation.Level level;
	private Affiliation parent;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
	private Set<Affiliation> children = new HashSet<Affiliation>();
	private Set<TeamAffiliation> affiliatedTeams = new HashSet<TeamAffiliation>();
}
