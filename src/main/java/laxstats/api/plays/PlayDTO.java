package laxstats.api.plays;

import laxstats.query.events.EventEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class PlayDTO {

	private PlayType discriminator;
	private PlayKey playKey;
	private EventEntry event;
	private TeamEntry team;
	private int sequence;
	private int period;
	private LocalTime elapsedTime;
	private ScoreAttemptType attemptType;
	private PlayResult result;
	private int teamScore;
	private int opponentScore;
	private Strength strength;
	private int manUpAdvantage;
	private TeamEntry manUpTeam;
	private String comment;
	private LocalDateTime createdAt;
	private UserEntry createdBy;
	private LocalDateTime modifiedAt;
	private UserEntry modifiedBy;

	public PlayType getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(PlayType discriminator) {
		this.discriminator = discriminator;
	}

	public PlayKey getPlayKey() {
		return playKey;
	}

	public void setPlayKey(PlayKey playKey) {
		this.playKey = playKey;
	}

	public EventEntry getEvent() {
		return event;
	}

	public void setEvent(EventEntry event) {
		this.event = event;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public void setTeam(TeamEntry team) {
		this.team = team;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
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

	public ScoreAttemptType getAttemptType() {
		return attemptType;
	}

	public void setAttemptType(ScoreAttemptType attemptType) {
		this.attemptType = attemptType;
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

	public TeamEntry getManUpTeam() {
		return manUpTeam;
	}

	public void setManUpTeam(TeamEntry manUpTeam) {
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
}
