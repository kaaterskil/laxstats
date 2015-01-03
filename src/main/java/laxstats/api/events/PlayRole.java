package laxstats.api.events;

public enum PlayRole {
	SCORER("Scorer"), ASSIST("Assist"), GOALIE("Goalie"), SHOOTER("Shooter"), BLOCKER(
			"Blocker"), GROUND_BALL("Ground Ball"), PENALTY_COMMITTED_BY(
			"Penalty Committed By"), PENALTY_COMMITTED_AGAINST(
			"Penalty Committed Against"), FACEOFF_WINNER("Faceoff Winner"), FACEOFF_LOSER(
			"Faceoff Loser");

	private String label;

	private PlayRole(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
