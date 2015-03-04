package laxstats.web.events;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;
import laxstats.api.events.ScoreAttemptType;

import org.joda.time.Period;

public class GoalForm extends AbstractPlayForm {
	private Period elapsedTime;
	private String scorerId;
	private String assistId;
	private ScoreAttemptType attemptType;

	public GoalForm() {
		super(PlayType.GOAL, PlayKey.GOAL);
	}

	/*---------- Getter/Setters ----------*/

	public Period getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Period elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getScorerId() {
		return scorerId;
	}

	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}

	public String getAssistId() {
		return assistId;
	}

	public void setAssistId(String assistId) {
		this.assistId = assistId;
	}

	public ScoreAttemptType getAttemptType() {
		return attemptType;
	}

	public void setAttemptType(ScoreAttemptType attemptType) {
		this.attemptType = attemptType;
	}
}
