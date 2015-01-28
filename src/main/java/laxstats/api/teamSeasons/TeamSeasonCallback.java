package laxstats.api.teamSeasons;

import org.axonframework.commandhandling.CommandCallback;

public class TeamSeasonCallback implements CommandCallback<String> {
	private String result;
	private Throwable cause;

	public String getResult() {
		return result;
	}

	public Throwable getCause() {
		return cause;
	}

	@Override
	public void onSuccess(String result) {
		this.result = result;
	}

	@Override
	public void onFailure(Throwable cause) {
		this.cause = cause;
	}

}
