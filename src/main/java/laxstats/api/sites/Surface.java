package laxstats.api.sites;

public enum Surface {
	UNKNOWN("Unknown"), GRASS("Grass"), TURF("Turf");
	private String value;

	private Surface(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
