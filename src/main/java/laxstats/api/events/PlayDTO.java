package laxstats.api.events;

import java.util.ArrayList;
import java.util.List;

import laxstats.query.events.GameEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;
import laxstats.query.violations.ViolationEntry;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class PlayDTO {
	private final String identifier;
	private final String discriminator;
	private final PlayKey playKey;
	private final GameEntry event;
	private final TeamSeasonEntry team;
	private final int period;
	private final LocalTime elapsedTime;
	private final ScoreAttemptType attemptType;
	private final PlayResult result;
	private final ViolationEntry violation;
	private final int penaltyDuration;
	private final String comment;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;
	private List<PlayParticipantDTO> participants = new ArrayList<>();

	// Computed values
	private int sequence = -1;
	private int teamScore = 0;
	private int opponentScore = 0;
	private Strength strength = Strength.EVEN_STRENGTH;
	private int manUpAdvantage = 0;
	private TeamSeasonEntry manUpTeam;

	public PlayDTO(String identifier, String discriminator, PlayKey playKey,
			GameEntry event, TeamSeasonEntry team, int period,
			LocalTime elapsedTime, ScoreAttemptType attemptType,
			PlayResult result, ViolationEntry violation, int duration,
			String comment, LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy,
			List<PlayParticipantDTO> participants) {
		this.identifier = identifier;
		this.discriminator = discriminator;
		this.playKey = playKey;
		this.event = event;
		this.team = team;
		this.period = period;
		this.elapsedTime = elapsedTime;
		this.attemptType = attemptType;
		this.result = result;
		this.violation = violation;
		this.penaltyDuration = duration;
		this.comment = comment;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
		this.participants = participants;
	}

	public PlayDTO(String identifier, String discriminator, PlayKey playKey,
			GameEntry event, TeamSeasonEntry team, int period,
			LocalTime elapsedTime, ScoreAttemptType attemptType,
			PlayResult result, ViolationEntry violation, int duration,
			String comment, LocalDateTime modifiedAt, UserEntry modifiedBy,
			List<PlayParticipantDTO> participants) {
		this(identifier, discriminator, playKey, event, team, period,
				elapsedTime, attemptType, result, violation, duration, comment,
				null, null, modifiedAt, modifiedBy, participants);
	}

	/*---------- Methods ----------*/

	public LocalTime getTotalElapsedTime() {
		return PlayUtils.getTotalElapsedTime(period, elapsedTime);
	}

	public DateTime getInstant() {
		return PlayUtils.getInstant(event.getStartsAt(), period, elapsedTime);
	}

	/*---------- Getters ----------*/

	public String getIdentifier() {
		return identifier;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public PlayKey getPlayKey() {
		return playKey;
	}

	public GameEntry getEvent() {
		return event;
	}

	public TeamSeasonEntry getTeam() {
		return team;
	}

	public int getPeriod() {
		return period;
	}

	public LocalTime getElapsedTime() {
		return elapsedTime;
	}

	public ScoreAttemptType getAttemptType() {
		return attemptType;
	}

	public PlayResult getResult() {
		return result;
	}

	public ViolationEntry getViolation() {
		return violation;
	}

	public int getPenaltyDuration() {
		return penaltyDuration;
	}

	public String getComment() {
		return comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public List<PlayParticipantDTO> getParticipants() {
		return participants;
	}

	/*---------- Getter/Setters for computed values ----------*/

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
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
}
