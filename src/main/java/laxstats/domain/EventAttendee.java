package laxstats.domain;

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

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "people_events_idx1", columnList = "role"),
		@Index(name = "people_events_idx2", columnList = "status")
	}
)
public class EventAttendee {
	
	public enum Status {
		STARTED, PLAYED;
	}
	
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 1582104599083216078L;

		@Column(length = 36)
		private String personId;
		
		@Column(length = 36)
		private String eventId;
		
		public Id(){}
		
		public Id(String personId, String eventId){
			this.personId = personId;
			this.eventId = eventId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof EventAttendee.Id) {
				Id that = (Id) o;
				return this.personId.equals(that.personId) && 
						this.eventId.equals(that.eventId);
			}
			return false;
		}
		
		public int hashCode() {
			return personId.hashCode() + eventId.hashCode();
		}
	}
	
	@javax.persistence.Id
	@Embedded
	private EventAttendee.Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "personId", insertable = false, updatable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "eventId", insertable = false, updatable = false)
	private Event event;
	
	@ManyToOne(targetEntity = Team.class)
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
	
	@ManyToOne(targetEntity = User.class)
	private String createdBy;
	
	//---------- Constructors ----------//
	
	public EventAttendee(){}
	
	public EventAttendee(Person person, Event event){
		this.person = person;
		this.event = event;
		
		this.id.personId = person.getId();
		this.id.eventId = event.getId();
		
		person.getAttendedEvents().add(this);
		event.getEventAttendees().add(this);
	}
	
	//---------- Getter/Setters ----------//

	public EventAttendee.Id getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
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
