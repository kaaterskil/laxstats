package laxstats.api.players;

public enum PlayerStatus {
	ACTIVE("Active"), INJURED("Injured"), TRYOUT("Tryout"), INACTIVE("Inactive");

	private String label;

	private PlayerStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
