package laxstats.query.events;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import laxstats.api.events.Conditions;
import laxstats.api.events.Schedule;
import laxstats.api.events.Status;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "events", indexes = {
		@Index(name = "event_idx1", columnList = "schedule"),
		@Index(name = "event_idx2", columnList = "startsAt"),
		@Index(name = "event_idx3", columnList = "alignment"),
		@Index(name = "event_idx4", columnList = "status"),
		@Index(name = "event_idx5", columnList = "conditions") })
public class EventEntry {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	private SiteEntry site;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private SiteAlignment alignment;

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

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private final Set<EventAttendee> eventAttendees = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private final List<TeamEvent> teams = new ArrayList<>();

	// ---------- Getter/Setters ----------//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SiteEntry getSite() {
		return site;
	}

	public void setSite(SiteEntry site) {
		this.site = site;
	}

	public SiteAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(SiteAlignment alignment) {
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

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntry createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserEntry modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Set<EventAttendee> getEventAttendees() {
		return eventAttendees;
	}

	public List<TeamEvent> getTeams() {
		return teams;
	}
}
