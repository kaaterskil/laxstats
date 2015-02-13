package laxstats.api.sites;

public enum SiteStyle {
	UNKNOWN("Unknown"), COMPETITION("Competition"), PRACTICE("Practice");
	private String label;

	private SiteStyle(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
