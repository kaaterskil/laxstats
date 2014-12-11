package laxstats.query.plays;

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

import laxstats.query.people.PersonEntry;
import laxstats.query.teams.Team;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "play_participant_idx1", columnList = "role"),
		@Index(name = "play_participant_idx2", columnList = "pointCredit")
	}
)
public class PlayParticipantEntry {
	
	public enum Role {
		SCORER, ASSIST, GOALIE, SHOOTER, BLOCKER, GROUND_BALL, 
		PENALTY_COMMITTED_BY, PENALTY_COMMITTED_AGAINST, 
		FACEOFF_WINNER, FACEOFF_LOSER;
	}
	
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 912492002722920566L;

		@Column(length = 36)
		private String playId;
		
		@Column(length = 36)
		private String personId;
		
		@Column(length = 36)
		private String teamId;
		
		public Id(){}
		
		public Id(String playId, String personId, String teamId) {
			this.playId = playId;
			this.personId = personId;
			this.teamId = teamId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof PlayParticipantEntry.Id) {
				Id that = (Id) o;
				return this.playId.equals(that.playId) && 
						this.personId.equals(that.personId) && 
						this.teamId.equals(that.teamId);
			}
			return false;
		}
		
		public int hashCode() {
			return playId.hashCode() + personId.hashCode() + teamId.hashCode();
		}
	}
	
	@javax.persistence.Id
	@Embedded
	private Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "playId", insertable = false, updatable = false)
	private PlayEntry play;
	
	@ManyToOne
	@JoinColumn(name = "personId", insertable = false, updatable = false)
	private PersonEntry person;
	
	@ManyToOne
	@JoinColumn(name = "teamId", insertable = false, updatable = false)
	private Team team;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private PlayParticipantEntry.Role role;
	
	private boolean pointCredit = false;
	
	private int cumulativeAssists;
	
	private int cumulativeGoals;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;
	
	//---------- Constructors ----------//
	
	protected PlayParticipantEntry(){}
	
	public PlayParticipantEntry(PlayEntry play, PersonEntry person, Team team) {
		this.play = play;
		this.person = person;
		this.team = team;
		
		this.id.playId = play.getId();
		this.id.personId = person.getId();
		this.id.teamId = team.getId();
	}
	
	//---------- Getter/Setters ----------//

	public PlayEntry getPlay() {
		return play;
	}

	public PersonEntry getPerson() {
		return person;
	}

	public Team getTeam() {
		return team;
	}

	public PlayParticipantEntry.Role getRole() {
		return role;
	}

	public void setRole(PlayParticipantEntry.Role role) {
		this.role = role;
	}

	public boolean isPointCredit() {
		return pointCredit;
	}

	public void setPointCredit(boolean pointCredit) {
		this.pointCredit = pointCredit;
	}

	public int getCumulativeAssists() {
		return cumulativeAssists;
	}

	public void setCumulativeAssists(int cumulativeAssists) {
		this.cumulativeAssists = cumulativeAssists;
	}

	public int getCumulativeGoals() {
		return cumulativeGoals;
	}

	public void setCumulativeGoals(int cumulativeGoals) {
		this.cumulativeGoals = cumulativeGoals;
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
