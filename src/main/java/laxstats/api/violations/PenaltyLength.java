package laxstats.api.violations;

public enum PenaltyLength {
	THIRTY_SECONDS("0:30", 30), ONE_MINUTE("1:00", 60), TWO_MINUTES("2:00", 120), THREE_MINUTES(
			"3:00", 180), EJECTION("Ejection", -1);

	private final String label;
	private int duration;

	private PenaltyLength(String label, int duration) {
		this.label = label;
		this.duration = duration;
	}

	public String getLabel() {
		return label;
	}

	public int getDuration() {
		return duration;
	}
}
