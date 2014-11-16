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
		@Index(name = "team_event_idx1", columnList = "outcome"),
		@Index(name = "team_event_idx2", columnList = "alignment")
	}
)
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

		@Column(length = 36)
		private String teamId;
		
		@Column(length = 36)
		private String eventId;
		
		public Id(){}
		
		public Id(String teamId, String eventID){
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
	@JoinColumn(name = "teamId", insertable = false, updatable = false)
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "eventId", insertable = false, updatable = false)
	private Event event;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Alignment alignment;
	
	private int score;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Outcome outcome;
	
	@Column(length = 100)
	private String scorekeeper;
	
	@Column(length = 100)
	private String timekeeper;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;
	
	@ManyToOne(targetEntity = User.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne(targetEntity = User.class)
	private String modifiedBy;
	
	//----------Constructor ----------//
	
	public TeamEvent(){}
	
	public TeamEvent(Team team, Event event) {
		this.team = team;
		this.event = event;
		
		this.id.teamId = team.getId();
		this.id.eventId = team.getId();
		
		team.getTeamEvents().add(this);
		event.getEventTeams().add(this);
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
}
