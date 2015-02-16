package laxstats.api.events;

public enum Alignment {
	HOME("Home"), AWAY("Away"), NEUTRAL("Neutral");
	private String label;

	private Alignment(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
