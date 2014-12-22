package laxstats.domain.leagues;

import org.joda.time.LocalDate;

public class TeamInfo {
	private String id;
	private String name;
	private LocalDate startingOn;
	private LocalDate endingOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartingOn() {
		return startingOn;
	}

	public void setStartingOn(LocalDate startingOn) {
		this.startingOn = startingOn;
	}

	public LocalDate getEndingOn() {
		return endingOn;
	}

	public void setEndingOn(LocalDate endingOn) {
		this.endingOn = endingOn;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof TeamInfo) {
			final TeamInfo that = (TeamInfo) o;
			return this.id.equals(that.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
