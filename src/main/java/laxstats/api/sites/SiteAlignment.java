package laxstats.api.sites;

public enum SiteAlignment {
	HOME("Home"), NEUTRAL("Neutral");
	private String label;

	private SiteAlignment(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
