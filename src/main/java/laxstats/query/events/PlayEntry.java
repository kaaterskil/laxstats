package laxstats.query.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayResult;
import laxstats.api.events.ScoreAttemptType;
import laxstats.api.events.Strength;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;
import laxstats.query.violations.ViolationEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "playType", length = 20, discriminatorType = DiscriminatorType.STRING)
@Table(name = "plays", indexes = {
		@Index(name = "play_idx1", columnList = "playType"),
		@Index(name = "play_idx2", columnList = "event, playType"),
		@Index(name = "play_idx3", columnList = "period"),
		@Index(name = "play_idx4", columnList = "strength"),
		@Index(name = "play_idx5", columnList = "playKey"),
		@Index(name = "play_idx6", columnList = "result"),
		@Index(name = "play_idx7", columnList = "playKey, result"),
		@Index(name = "play_idx8", columnList = "period, elapsedTime") }, uniqueConstraints = { @UniqueConstraint(name = "play_uk1", columnNames = {
		"event", "sequenceNumber" }) })
abstract public class PlayEntry implements Serializable {
	private static final long serialVersionUID = -9074132185978497348L;

	@Id
	@Column(length = 36)
	protected String id;

	@ManyToOne
	protected GameEntry event;

	@Column(name = "playType", insertable = false, updatable = false)
	protected String playType;

	protected int sequenceNumber;

	@ManyToOne
	protected TeamSeasonEntry team;

	protected int period;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentPeriodAsString")
	protected Period elapsedTime;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	protected PlayKey playKey;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	protected ScoreAttemptType scoreAttemptType;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	protected PlayResult result;

	@Column(columnDefinition = "text")
	protected String comment;

	/*----- Goal properties -----*/

	protected int teamScore;

	protected int opponentScore;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	protected Strength strength;

	protected int manUpAdvantage;

	@ManyToOne
	protected TeamSeasonEntry manUpTeam;

	/*----- Penalty properties -----*/

	@ManyToOne
	protected ViolationEntry violation;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentPeriodAsString")
	protected Period duration;

	/*----- Audit properties -----*/

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	protected LocalDateTime createdAt;

	@ManyToOne
	protected UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	protected LocalDateTime modifiedAt;

	@ManyToOne
	protected UserEntry modifiedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "play")
	protected final List<PlayParticipantEntry> participants = new ArrayList<>();

	/*---------- Methods ----------*/

	public void addParticipant(PlayParticipantEntry participant) {
		participant.setPlay(this);
		participants.add(participant);
	}

	public void removeParticipant(PlayParticipantEntry participant) {
		participant.clear();
		participants.remove(participant);
	}

	public void clear() {
		event = null;
		team = null;
		manUpTeam = null;
		createdBy = null;
		modifiedBy = null;

		for (final PlayParticipantEntry each : participants) {
			each.clear();
		}
		participants.clear();
	}

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayType() {
		return playType;
	}

	public GameEntry getEvent() {
		return event;
	}

	public void setEvent(GameEntry event) {
		this.event = event;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public TeamSeasonEntry getTeam() {
		return team;
	}

	public void setTeam(TeamSeasonEntry team) {
		this.team = team;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Period getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Period elapsedTime) {
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

	public PlayResult getResult() {
		return result;
	}

	public void setResult(PlayResult result) {
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

	public TeamSeasonEntry getManUpTeam() {
		return manUpTeam;
	}

	public void setManUpTeam(TeamSeasonEntry manUpTeam) {
		this.manUpTeam = manUpTeam;
	}

	public ViolationEntry getViolation() {
		return violation;
	}

	public void setViolation(ViolationEntry violation) {
		this.violation = violation;
	}

	public Period getDuration() {
		return duration;
	}

	public void setDuration(Period duration) {
		this.duration = duration;
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

	public List<PlayParticipantEntry> getParticipants() {
		return participants;
	}
}
