package laxstats.api.teams;

public enum Letter {
	VARSITY("Varsity"), JV("Junior Varsity"), FRESHMAN("Freshman"), YOUTH(
			"Youth");
	private String label;

	private Letter(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
