package laxstats.api.sites;

public enum SiteStyle {
	UNKNOWN("Unknown"), COMPETITION("Competition"), PRACTICE("Practice");
	private String value;

	private SiteStyle(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
