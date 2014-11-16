package laxstats.domain.events;

import java.util.HashSet;
import java.util.Set;

import laxstats.query.events.PlayParticipant;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

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

	private String id;
	private Event event;
	private int sequenceNumber;
	private String teamId;
	private int period;
	private LocalTime elapsedTime;
	protected PlayKey playKey;
	private ScoreAttemptType scoreAttemptType;
	private Result result;
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
	private Set<PlayParticipant> participants = new HashSet<PlayParticipant>();
}
