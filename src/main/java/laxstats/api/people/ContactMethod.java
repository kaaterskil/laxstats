package laxstats.api.people;

public enum ContactMethod {
	MOBILE("Mobile"), EMAIL("Email"), TELEPHONE("Telephone"), FAX("Fax"), PAGER(
			"Pager");

	private String label;

	private ContactMethod(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
