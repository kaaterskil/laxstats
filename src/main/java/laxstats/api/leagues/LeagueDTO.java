package laxstats.api.leagues;

import laxstats.query.leagues.LeagueEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class LeagueDTO {
	private final String name;
	private final Level level;
	private final String description;
	private final LeagueEntry parent;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public LeagueDTO(String name, Level level, String description,
			LeagueEntry parent, LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.name = name;
		this.level = level;
		this.description = description;
		this.parent = parent;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public LeagueDTO(String name, Level level, String description,
			LeagueEntry parent, LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(name, level, description, parent, null, null, modifiedAt,
				modifiedBy);
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

	public LeagueEntry getParent() {
		return parent;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

}
