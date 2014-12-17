package laxstats.domain.leagues;

import laxstats.api.leagues.Level;

public class LeagueInfo {
	private final String name;
	private final Level level;
	private final String description;

	public LeagueInfo(String name, Level level, String description) {
		this.name = name;
		this.level = level;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public Level getLevel() {
		return level;
	}

	public String getDescription() {
		return description;
	}

}
