package laxstats.api.events;

public enum Conditions {
	RAIN("Rain"), SUNNY("Sunny"), CLEAR("Clear"), PARTLY_CLOUDY("Party Cloudy"), CLOUDY(
			"Cloudy"), MOSTLY_CLOUDY("Mostly Cloudy"), WINDY("Windy"), SNOW(
			"Snow"), SHOWERS("Showers");
	private String label;

	private Conditions(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
