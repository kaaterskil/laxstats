package laxstats.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
public class TeamEvent {
	
	public enum Alignment {
		HOME, AWAY;
	}
	
	public enum Outcome {
		WIN, LOSS;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = -3438452317292121148L;

		@Column(name = "team_id")
		private UUID teamId;
		
		@Column(name = "event_id")
		private UUID eventId;
		
		public Id(){}
		
		public Id(UUID teamId, UUID eventID){
			this.teamId = teamId;
			this.eventId = eventID;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof TeamEvent.Id) {
				Id that = (Id) o;
				return this.teamId.equals(that.teamId) && this.eventId.equals(that.eventId);
			}
			return false;
		}
		
		public int hashCode() {
			return teamId.hashCode() + eventId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private TeamEvent.Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "team_id", insertable = false, updatable = false)
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "event_id", insertable = false, updatable = false)
	private Event event;
	
	@Enumerated(EnumType.STRING)
	private Alignment alignment;
	
	private int score;
	
	@Enumerated(EnumType.STRING)
	private Outcome outcome;
	
	private String scorekeeper;
	
	@Column(name = "penalty_timekeeper")
	private String timekeeper;
	
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
	
	//----------Constructor ----------//
	
	public TeamEvent(){}
	
	public TeamEvent(Team team, Event event) {
		this.team = team;
		this.event = event;
		
		this.id.teamId = team.getId();
		this.id.eventId = team.getId();
		
		team.getTeamEvents().add(this);
		event.getTeamEvents().add(this);
	}
	
	//---------- Getter/Setters ----------//

	public TeamEvent.Id getId() {
		return id;
	}

	public Team getTeam() {
		return team;
	}

	public Event getEvent() {
		return event;
	}	

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Outcome getOutcome() {
		return outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

	public String getScorekeeper() {
		return scorekeeper;
	}

	public void setScorekeeper(String scorekeeper) {
		this.scorekeeper = scorekeeper;
	}

	public String getTimekeeper() {
		return timekeeper;
	}

	public void setTimekeeper(String timekeeper) {
		this.timekeeper = timekeeper;
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
}
