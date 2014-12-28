package laxstats.query.events;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import laxstats.api.players.Role;
import laxstats.query.people.PersonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = { @Index(name = "people_events_idx1", columnList = "role"),
		@Index(name = "people_events_idx2", columnList = "status") })
public class EventAttendee {

	public enum Status {
		STARTED, PLAYED
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 1582104599083216078L;

		@Column(length = 36)
		private String personId;

		@Column(length = 36)
		private String eventId;

		public Id() {
		}

		public Id(String personId, String eventId) {
			this.personId = personId;
			this.eventId = eventId;
		}

		@Override
		public boolean equals(Object o) {
			if (o != null && o instanceof EventAttendee.Id) {
				final Id that = (Id) o;
				return this.personId.equals(that.personId)
						&& this.eventId.equals(that.eventId);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return personId.hashCode() + eventId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private final EventAttendee.Id id = new Id();

	@ManyToOne
	@JoinColumn(name = "personId", insertable = false, updatable = false)
	private PersonEntry person;

	@ManyToOne
	@JoinColumn(name = "eventId", insertable = false, updatable = false)
	private EventEntry event;

	@ManyToOne(targetEntity = TeamEntry.class)
	private String teamId;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Role role;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Status status;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;

	// ---------- Constructors ----------//

	public EventAttendee() {
	}

	public EventAttendee(PersonEntry person, EventEntry event) {
		this.person = person;
		this.event = event;

		this.id.personId = person.getId();
		this.id.eventId = event.getId();

		person.getAttendedEvents().add(this);
		event.getEventAttendees().add(this);
	}

	// ---------- Getter/Setters ----------//

	public EventAttendee.Id getId() {
		return id;
	}

	public PersonEntry getPerson() {
		return person;
	}

	public void setPerson(PersonEntry person) {
		this.person = person;
	}

	public EventEntry getEvent() {
		return event;
	}

	public void setEvent(EventEntry event) {
		this.event = event;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
}
