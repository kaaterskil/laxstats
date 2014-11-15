package laxstats.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "play_type", discriminatorType = DiscriminatorType.STRING)
abstract public class Play {
	
	public enum PlayKey {
		PLAY, GOAL;
	}
	
	public enum Strength {
		EVEN_STRENGTH, MAN_UP, MAN_DOWN;
	}
	
	public enum ScoreAttemptType {
		REGULAR, PENALTY_SHOT, EMPTY_NET, OWN_GOAL;
	}
	
	public enum Result {
		GOAL, SHOT_MISSED, SHOT_SAVED, SHOT_BLOCKED, SHOT_OFF_POST, CLEAR_SUCCEEDED, CLEAR_FAILED;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne
	private Event event;
	
	private int sequenceNumber;
	
	@ManyToOne
	private Team team;
	
	private int period;
	
	@Column(name = "elapsed_time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	private LocalTime elapsedTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "play_key")
	protected PlayKey playKey;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "score_attmept_type")
	private ScoreAttemptType scoreAttemptType;
	
	@Enumerated(EnumType.STRING)
	private Result result;
	
	@Column(name = "team_score")
	private int teamScore;
	
	@Column(name = "opponent_score")
	private int opponentScore;
	
	@Enumerated(EnumType.STRING)
	private Strength strength;
	
	@Column(name = "man_up_advantage")
	private int manUpAdvantage;
	
	@ManyToOne
	@JoinColumn(name = "man_up_team_id")
	private Team manUpTeam;
	
	private String comment;
	
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
	
	@OneToMany
	private Set<PlayParticipant> participants = new HashSet<PlayParticipant>();
	
	//---------- Getter/Setters ----------//

	public UUID getId() {
		return id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public LocalTime getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(LocalTime elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public PlayKey getPlayKey() {
		return playKey;
	}

	public void setPlayKey(PlayKey playKey) {
		this.playKey = playKey;
	}

	public ScoreAttemptType getScoreAttemptType() {
		return scoreAttemptType;
	}

	public void setScoreAttemptType(ScoreAttemptType scoreAttemptType) {
		this.scoreAttemptType = scoreAttemptType;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public int getTeamScore() {
		return teamScore;
	}

	public void setTeamScore(int teamScore) {
		this.teamScore = teamScore;
	}

	public int getOpponentScore() {
		return opponentScore;
	}

	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}

	public Strength getStrength() {
		return strength;
	}

	public void setStrength(Strength strength) {
		this.strength = strength;
	}

	public int getManUpAdvantage() {
		return manUpAdvantage;
	}

	public void setManUpAdvantage(int manUpAdvantage) {
		this.manUpAdvantage = manUpAdvantage;
	}

	public Team getManUpTeam() {
		return manUpTeam;
	}

	public void setManUpTeam(Team manUpTeam) {
		this.manUpTeam = manUpTeam;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Set<PlayParticipant> getParticipants() {
		return participants;
	}
	
	public boolean addParticipant(PlayParticipant participant) {
		participant.setPlay(this);
		return participants.add(participant);
	}
}
