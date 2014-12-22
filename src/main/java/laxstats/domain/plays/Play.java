package laxstats.domain.plays;

import java.util.HashSet;
import java.util.Set;

import laxstats.api.plays.AbstractPlayCreatedEvent;
import laxstats.api.plays.PlayDTO;
import laxstats.api.plays.PlayId;
import laxstats.api.plays.PlayKey;
import laxstats.api.plays.PlayResult;
import laxstats.api.plays.ScoreAttemptType;
import laxstats.api.plays.Strength;
import laxstats.query.plays.PlayParticipantEntry;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalTime;

public class Play extends AbstractAnnotatedAggregateRoot<PlayId> {

	@AggregateIdentifier
	private PlayId id;
	private String eventId;
	private int sequenceNumber;
	private String teamId;
	private int period;
	private LocalTime elapsedTime;
	protected PlayKey playKey;
	private ScoreAttemptType scoreAttemptType;
	private PlayResult result;
	private int teamScore;
	private int opponentScore;
	private Strength strength;
	private int manUpAdvantage;
	private String manUpTeamId;
	private String comment;
	private final Set<PlayParticipantEntry> participants = new HashSet<>();

	// ---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(AbstractPlayCreatedEvent event) {
		final PlayId playId = event.getPlayId();
		final PlayDTO dto = event.getPlayDTO();

		id = playId;
		eventId = dto.getEvent().getId();
		sequenceNumber = dto.getSequence();
		teamId = dto.getTeam().getId();
		period = dto.getPeriod();
		elapsedTime = dto.getElapsedTime();
		playKey = dto.getPlayKey();
		scoreAttemptType = dto.getAttemptType();
		result = dto.getResult();
		teamScore = dto.getTeamScore();
		opponentScore = dto.getOpponentScore();
		strength = dto.getStrength();
		manUpAdvantage = dto.getManUpAdvantage();
		manUpTeamId = dto.getManUpTeam().getId();
		comment = dto.getComment();
	}

	// ---------- Getters ----------//

	public PlayId getId() {
		return id;
	}

	public String getEventId() {
		return eventId;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public String getTeamId() {
		return teamId;
	}

	public int getPeriod() {
		return period;
	}

	public LocalTime getElapsedTime() {
		return elapsedTime;
	}

	public PlayKey getPlayKey() {
		return playKey;
	}

	public ScoreAttemptType getScoreAttemptType() {
		return scoreAttemptType;
	}

	public PlayResult getResult() {
		return result;
	}

	public int getTeamScore() {
		return teamScore;
	}

	public int getOpponentScore() {
		return opponentScore;
	}

	public Strength getStrength() {
		return strength;
	}

	public int getManUpAdvantage() {
		return manUpAdvantage;
	}

	public String getManUpTeamId() {
		return manUpTeamId;
	}

	public String getComment() {
		return comment;
	}
}
