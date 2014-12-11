package laxstats.api.plays;

public enum PenaltyLength {
	THIRTY_SECONDS("0:30"), ONE_MINUTE("1:00"), TWO_MINUTES("2:00"), THREE_MINUTES(
			"3:00"), EJECTION("Ejection");

	private String value;

	private PenaltyLength(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
