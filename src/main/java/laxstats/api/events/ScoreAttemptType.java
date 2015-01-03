package laxstats.api.events;

public enum ScoreAttemptType {
	REGULAR("Regular"), PENALTY_SHOT("Penalty Shot"), EMPTY_NET("Empty Net"), OWN_GOAL(
			"Own Goal");

	private String label;

	private ScoreAttemptType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
