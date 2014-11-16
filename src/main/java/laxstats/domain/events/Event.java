package laxstats.domain.events;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDateTime;

public class Event {

	public enum Alignment {
		HOME, NEUTRAL;
	}

	public enum Conditions {
		RAIN, SUNNY, CLEAR, PARTLY_CLOUDY, CLOUDY, MOSTLY_CLOUDY, WINDY, SNOW, SHOWERS;
	}

	public enum Status {
		SCHEDULED, OCCURRED, POSTPONED, SUSPENDED, HALTED, FORFEITED, RESCHEDULED, DELAYED, CANCELLED, IF_NECESSARY, DISCARDED;
	}

	public enum Schedule {
		PRE_SEASON, REGULAR, POST_SEASON, EXHIBITION;
	}

	private String id;
	private String siteId;
	private Alignment alignment;
	private LocalDateTime startsAt;
	private Schedule schedule;
	private Status status;
	private Conditions conditions;
	private String description;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
	private Set<EventAttendee> eventAttendees = new HashSet<EventAttendee>();
	private Set<TeamEvent> eventTeams = new HashSet<TeamEvent>();
}
