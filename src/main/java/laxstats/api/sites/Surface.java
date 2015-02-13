package laxstats.api.sites;

public enum Surface {
	UNKNOWN("Unknown"), GRASS("Grass"), TURF("Turf");
	private String label;

	private Surface(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
