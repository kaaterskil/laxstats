package laxstats.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "event_idx1", columnList = "schedule"),
		@Index(name = "event_idx2", columnList = "starts_at"),
		@Index(name = "event_idx3", columnList = "alignment"),
		@Index(name = "event_idx4", columnList = "status"),
		@Index(name = "event_idx5", columnList = "conditions")
	}
)
public class Event {
	
	public enum Alignment {
		HOME, NEUTRAL;
	}
	
	public enum Conditions {
		RAIN, SUNNY, CLEAR, PARTLY_CLOUDY, CLOUDY, MOSTLY_CLOUDY, WINDY, SNOW, SHOWERS;
	}
	
	public enum Status {
		SCHEDULED, OCCURRED, POSTPONED, SUSPENDED, HALTED, FORFEITED, RESCHEDULED, DELAYED, 
		CANCELLED, IF_NECESSARY, DISCARDED;
	}
	
	public enum Schedule {
		PRE_SEASON, REGULAR, POST_SEASON, EXHIBITION;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	private Site site;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Alignment alignment;
	
	@Column(name = "starts_at")
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
	
	@Column(name = "created_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	@Column(name = "modified_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne
	@JoinColumn(name = "modified_by")
	private User modifiedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private Set<EventAttendee> eventAttendees = new HashSet<EventAttendee>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private Set<TeamEvent> eventTeams = new HashSet<TeamEvent>();
	
	//---------- Getter/Setters ----------//

	public UUID getId() {
		return id;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Set<EventAttendee> getEventAttendees() {
		return eventAttendees;
	}

	public Set<TeamEvent> getEventTeams() {
		return eventTeams;
	}
}
