package laxstats.web.events;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayResult;
import laxstats.api.events.PlayType;
import laxstats.api.events.ScoreAttemptType;

public class ShotForm extends AbstractPlayForm {
	private String playerId;
	private ScoreAttemptType attemptType;
	private PlayResult result;
	private List<PlayResult> results = new ArrayList<>();

	public ShotForm() {
		super(PlayType.SHOT, PlayKey.PLAY);
	}

	/*---------- Getter/Setters ----------*/

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

	public List<PlayResult> getResults() {
		return results;
	}

	public void setResults(List<PlayResult> results) {
		this.results = results;
	}
}
