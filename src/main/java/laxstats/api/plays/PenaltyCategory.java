package laxstats.api.plays;

public enum PenaltyCategory {
	PERSONAL_FOUL("Personal"), TEAM_FOUL("Team");

	private String value;

	private PenaltyCategory(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
