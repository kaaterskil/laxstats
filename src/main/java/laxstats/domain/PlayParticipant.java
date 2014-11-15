package laxstats.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
public class PlayParticipant {
	
	public enum Role {
		SCORER, ASSIST, GOALIE, SHOOTER, BLOCKER, GROUND_BALL, PENALTY_COMMITTED_BY, 
		PENALTY_COMMITTED_AGAINST, FACEOFF_WINNER, FACEOFF_LOSER;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@NotNull
	@ManyToOne
	private Play play;
	
	@NotNull
	@ManyToOne
	private Person person;
	
	@NotNull
	@ManyToOne
	private Team team;
	
	@Enumerated(EnumType.STRING)
	private PlayParticipant.Role role;
	
	@Column(name = "point_credit")
	private boolean pointCredit = false;
	
	@Column(name = "cumulative_assists")
	private int cumulativeAssists;
	
	@Column(name = "cumulative_goals")
	private int cumulativeGoals;
	
	@Column(name = "created_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	//---------- Getter/Setters ----------//

	public UUID getId() {
		return id;
	}

	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public PlayParticipant.Role getRole() {
		return role;
	}

	public void setRole(PlayParticipant.Role role) {
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
}
