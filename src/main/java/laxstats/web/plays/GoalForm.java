package laxstats.web.plays;

import org.joda.time.LocalTime;

public class GoalForm extends AbstractPlayForm {
	private LocalTime elapsedTime;
	private String scorerId;
	private String assistId;

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
}
