package laxstats.api.events;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.plays.PlayId;
import laxstats.query.events.EventEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class PlayDTO {
	private final PlayId identifier;
	private final String discriminator;
	private final PlayKey playKey;
	private final EventEntry event;
	private final TeamSeasonEntry team;
	private final int period;
	private final LocalTime elapsedTime;
	private final ScoreAttemptType attemptType;
	private final PlayResult result;
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

	public PlayDTO(PlayId identifier, String discriminator, PlayKey playKey,
			EventEntry event, TeamSeasonEntry team, int period,
			LocalTime elapsedTime, ScoreAttemptType attemptType,
			PlayResult result, String comment, LocalDateTime createdAt,
			UserEntry createdBy, LocalDateTime modifiedAt,
			UserEntry modifiedBy, List<PlayParticipantDTO> participants) {
		this.identifier = identifier;
		this.discriminator = discriminator;
		this.playKey = playKey;
		this.event = event;
		this.team = team;
		this.period = period;
		this.elapsedTime = elapsedTime;
		this.attemptType = attemptType;
		this.result = result;
		this.comment = comment;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
		this.participants = participants;
	}

	public PlayDTO(PlayId identifier, String discriminator, PlayKey playKey,
			EventEntry event, TeamSeasonEntry team, int period,
			LocalTime elapsedTime, ScoreAttemptType attemptType,
			PlayResult result, String comment, LocalDateTime modifiedAt,
			UserEntry modifiedBy, List<PlayParticipantDTO> participants) {
		this(identifier, discriminator, playKey, event, team, period,
				elapsedTime, attemptType, result, comment, null, null,
				modifiedAt, modifiedBy, participants);
	}

	/*---------- Methods ----------*/

	public LocalTime getTotalElapsedTime() {
		return PlayUtils.getTotalElapsedTime(period, elapsedTime);
	}

	/*---------- Getters ----------*/

	public PlayId getIdentifier() {
		return identifier;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public PlayKey getPlayKey() {
		return playKey;
	}

	public EventEntry getEvent() {
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
