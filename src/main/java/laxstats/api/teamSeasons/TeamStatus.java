package laxstats.api.teamSeasons;

public enum TeamStatus {
	ACTIVE("Active"), INACTIVE("Inactive");
	private String label;

	private TeamStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
