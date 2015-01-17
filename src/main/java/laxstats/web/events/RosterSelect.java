package laxstats.web.events;

public class RosterSelect {
	private final String id;
	private final String name;

	public RosterSelect(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
