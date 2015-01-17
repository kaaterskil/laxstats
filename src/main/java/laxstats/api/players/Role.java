package laxstats.api.players;

public enum Role {
	ATHLETE("Athlete"), COACH("Coach"), MANAGER("Manager"), ASSISTANT(
			"Assistant Coach"), OFFICIAL("Official"), BOOSTER("Booster"), COUNSELOR(
			"Counselor");
	private String label;

	private Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
