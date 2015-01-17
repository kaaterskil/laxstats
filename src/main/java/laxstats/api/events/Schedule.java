package laxstats.api.events;

public enum Schedule {
	PRE_SEASON("Pre-season"), REGULAR("Regular season"), POST_SEASON(
			"Post-season"), EXHIBITION("Exhibition");
	private String label;

	private Schedule(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
