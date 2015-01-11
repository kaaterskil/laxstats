package laxstats.web.events;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.events.PlayResult;

public class ClearForm extends AbstractPlayForm {
	private PlayResult result;
	private List<PlayResult> results = new ArrayList<>();

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
