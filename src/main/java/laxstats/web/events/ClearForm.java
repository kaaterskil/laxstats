package laxstats.web.events;

import laxstats.api.events.PlayResult;

public class ClearForm extends AbstractPlayForm {
	private PlayResult result;

	public PlayResult getResult() {
		return result;
	}

	public void setResult(PlayResult result) {
		this.result = result;
	}
}
