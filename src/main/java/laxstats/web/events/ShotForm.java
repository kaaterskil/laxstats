package laxstats.web.events;

import laxstats.api.events.PlayResult;
import laxstats.api.events.ScoreAttemptType;

public class ShotForm extends AbstractPlayForm {
	private String playerId;
	private ScoreAttemptType attemptType;
	private PlayResult result;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
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
}
