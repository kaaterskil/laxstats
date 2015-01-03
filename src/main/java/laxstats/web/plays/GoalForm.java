package laxstats.web.plays;

import java.util.HashMap;
import java.util.Map;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayResult;
import laxstats.api.events.PlayType;
import laxstats.api.events.ScoreAttemptType;
import laxstats.api.events.Strength;

import org.joda.time.LocalTime;

public class GoalForm {
	// Invariants
	private final String discriminator = PlayType.GOAL;
	private final PlayKey playKey = PlayKey.GOAL;
	private final PlayResult result = PlayResult.GOAL;

	// Computed values
	private String eventId;
	private int sequence;
	private int teamScore;
	private int opponentScore;
	private Strength strength;
	private int manUpAdvantage;
	private String manUpTeamId;

	// User input
	private String teamSeasonId;
	private int period;
	private LocalTime elapsedTime;
	private ScoreAttemptType attemptType;
	private String comment;

	// Option values
	private final Map<String, String> teams = new HashMap<>();
	private final Map<String, String> participants = new HashMap<>();
}
