package laxstats.web.leagues;

import javax.validation.constraints.NotNull;

import laxstats.api.leagues.Level;

public class LeagueForm {
	@NotNull
	private String name;

	@NotNull
	private Level level;
	private String description;
	private String parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
