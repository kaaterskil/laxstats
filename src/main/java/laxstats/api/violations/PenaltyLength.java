package laxstats.api.violations;

public enum PenaltyLength {
	THIRTY_SECONDS("0:30", 30), ONE_MINUTE("1:00", 60), TWO_MINUTES("2:00", 120), THREE_MINUTES(
			"3:00", 180), EJECTION("Ejection", -1);

	private final String value;
	private int duration;

	private PenaltyLength(String value, int duration) {
		this.value = value;
		this.duration = duration;
	}

	public String getValue() {
		return value;
	}

	public int getDuration() {
		return duration;
	}
}
