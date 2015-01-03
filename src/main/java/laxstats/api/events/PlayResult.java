package laxstats.api.events;

public enum PlayResult {
	GOAL("Goal"), SHOT_MISSED("Missed Shot"), SHOT_SAVED("Saved Shot"), SHOT_BLOCKED(
			"Blocked Shot"), SHOT_OFF_POST("Shot Off Post"), CLEAR_SUCCEEDED(
			"Clear Succeeded"), CLEAR_FAILED("Clear Failed");

	private String label;

	private PlayResult(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
