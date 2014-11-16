package laxstats.query.events;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.query.teams.Team;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	name = "playType",
	length = 20,
	discriminatorType = DiscriminatorType.STRING
)
@Table(
	indexes = {
		@Index(name = "play_idx1", columnList = "playType"),
		@Index(name = "play_idx2", columnList = "event, playType"),
		@Index(name = "play_idx3", columnList = "period"),
		@Index(name = "play_idx4", columnList = "strength"),
		@Index(name = "play_idx5", columnList = "playKey"),
		@Index(name = "play_idx6", columnList = "result"),
		@Index(name = "play_idx7", columnList = "playKey, result"),
		@Index(name = "play_idx8", columnList = "period, elapsedTime")
	},
	uniqueConstraints = {
		@UniqueConstraint(name = "play_uk1", columnNames = {"event", "sequenceNumber"})
	}
)
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
		GOAL, SHOT_MISSED, SHOT_SAVED, SHOT_BLOCKED, SHOT_OFF_POST, 
		CLEAR_SUCCEEDED, CLEAR_FAILED;
	}

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	private Event event;
	
	private int sequenceNumber;
	
	@ManyToOne(targetEntity = Team.class, optional = false)
	private String teamId;
	
	private int period;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	private LocalTime elapsedTime;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	protected PlayKey playKey;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ScoreAttemptType scoreAttemptType;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Result result;
	
	private int teamScore;
	
	private int opponentScore;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Strength strength;
	
	private int manUpAdvantage;
	
	@ManyToOne(targetEntity = Team.class)
	private String manUpTeamId;
	
	@Column(columnDefinition = "text")
	private String comment;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "play")
	private Set<PlayParticipant> participants = new HashSet<PlayParticipant>();
	
	//---------- Getter/Setters ----------//

	public String getId() {
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

	public String getTeamId() {
		return teamId;
	}

	public void setTeam(String teamId) {
		this.teamId = teamId;
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

	public String getManUpTeamId() {
		return manUpTeamId;
	}

	public void setManUpTeam(String manUpTeamId) {
		this.manUpTeamId = manUpTeamId;
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

	public Set<PlayParticipant> getParticipants() {
		return participants;
	}
}
