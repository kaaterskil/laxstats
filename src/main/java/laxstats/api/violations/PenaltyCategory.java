package laxstats.api.violations;

public enum PenaltyCategory {
	PERSONAL_FOUL("Personal"), TEAM_FOUL("Team");

	private final String value;

	private PenaltyCategory(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
