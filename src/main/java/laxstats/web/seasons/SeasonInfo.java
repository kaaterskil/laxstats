package laxstats.web.seasons;

public class SeasonInfo {
	private final String id;
	private final String description;
	private final String startsOn;
	private final String endsOn;

	public SeasonInfo(String id, String description, String startsOn,
			String endsOn) {
		this.id = id;
		this.description = description;
		this.startsOn = startsOn;
		this.endsOn = endsOn;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getStartsOn() {
		return startsOn;
	}

	public String getEndsOn() {
		return endsOn;
	}
}
