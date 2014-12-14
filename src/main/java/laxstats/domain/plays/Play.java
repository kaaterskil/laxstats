package laxstats.domain.plays;

import java.util.HashSet;
import java.util.Set;

import laxstats.api.plays.PlayKey;
import laxstats.api.plays.PlayResult;
import laxstats.api.plays.ScoreAttemptType;
import laxstats.api.plays.Strength;
import laxstats.query.plays.PlayParticipantEntry;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

abstract public class Play {

	private String id;
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
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
	private final Set<PlayParticipantEntry> participants = new HashSet<>();
}
