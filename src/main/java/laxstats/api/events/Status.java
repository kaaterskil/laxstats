package laxstats.api.events;

public enum Status {
	SCHEDULED("Scheduled"), OCCURRED("Occurred"), POSTPONED("Postponed"), SUSPENDED(
			"Suspended"), HALTED("Halted"), FORFEITED("Forfeited"), RESCHEDULED(
			"Rescheduled"), DELAYED("Delayed"), CANCELLED("Cancelled"), IF_NECESSARY(
			"If Necessary"), DISCARDED("Discarded");
	private String label;

	private Status(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
