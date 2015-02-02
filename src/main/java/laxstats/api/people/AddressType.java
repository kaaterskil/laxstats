package laxstats.api.people;

public enum AddressType {
	HOME("Home"), WORK("Work"), VACATION("Vacation"), SITE("Site");
	private String label;

	private AddressType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
