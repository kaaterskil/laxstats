package laxstats.api.events;

public enum AthleteStatus {
	STARTED("Starter"), PLAYED("Player");
	private String label;

	private AthleteStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
