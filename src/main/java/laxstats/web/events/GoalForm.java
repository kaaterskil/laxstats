package laxstats.web.events;

import laxstats.api.events.ScoreAttemptType;

import org.joda.time.LocalTime;

public class GoalForm extends AbstractPlayForm {
	private LocalTime elapsedTime;
	private String scorerId;
	private String assistId;
	private ScoreAttemptType attemptType;

	public LocalTime getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(LocalTime elapsedTime) {
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
