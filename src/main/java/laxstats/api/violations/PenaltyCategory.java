package laxstats.api.violations;

public enum PenaltyCategory {
	PERSONAL_FOUL("Personal"), TEAM_FOUL("Team");

	private final String label;

	private PenaltyCategory(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
