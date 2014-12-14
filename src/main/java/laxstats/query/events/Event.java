package laxstats.query.events;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "event_idx1", columnList = "schedule"),
		@Index(name = "event_idx2", columnList = "startsAt"),
		@Index(name = "event_idx3", columnList = "alignment"),
		@Index(name = "event_idx4", columnList = "status"),
		@Index(name = "event_idx5", columnList = "conditions")
	}
)
public class Event {
	
	public enum Alignment {
		HOME, NEUTRAL
	}
	
	public enum Conditions {
		RAIN, SUNNY, CLEAR, PARTLY_CLOUDY, CLOUDY, MOSTLY_CLOUDY, WINDY, 
		SNOW, SHOWERS
	}
	
	public enum Status {
		SCHEDULED, OCCURRED, POSTPONED, SUSPENDED, HALTED, FORFEITED, 
		RESCHEDULED, DELAYED, CANCELLED, IF_NECESSARY, DISCARDED
	}
	
	public enum Schedule {
		PRE_SEASON, REGULAR, POST_SEASON, EXHIBITION
	}

	@Id
	@Column(length = 36)
	private String id;
	
	@ManyToOne(targetEntity = SiteEntry.class)
	private String siteId;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Alignment alignment;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime startsAt;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Schedule schedule;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Conditions conditions;
	
	@NotNull
	@Column(nullable = false)
	private String description;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private final Set<EventAttendee> eventAttendees = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private final Set<TeamEvent> eventTeams = new HashSet<>();
	
	//---------- Getter/Setters ----------//

	public String getId() {
		return id;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(LocalDateTime startsAt) {
		this.startsAt = startsAt;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Conditions getConditions() {
		return conditions;
	}

	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Set<EventAttendee> getEventAttendees() {
		return eventAttendees;
	}

	public Set<TeamEvent> getEventTeams() {
		return eventTeams;
	}
}
