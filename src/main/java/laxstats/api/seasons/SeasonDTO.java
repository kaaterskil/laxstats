package laxstats.api.seasons;

import laxstats.query.users.UserEntry;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class SeasonDTO {
	private final SeasonId seasonId;
	private final String description;
	private final LocalDate startsOn;
	private final LocalDate endsOn;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public SeasonDTO(SeasonId seasonId, String description, LocalDate startsOn,
			LocalDate endsOn, LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		super();
		this.seasonId = seasonId;
		this.description = description;
		this.startsOn = startsOn;
		this.endsOn = endsOn;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public SeasonDTO(SeasonId seasonId, String description, LocalDate startsOn,
			LocalDate endsOn, LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(seasonId, description, startsOn, endsOn, null, null, modifiedAt,
				modifiedBy);
	}

	public SeasonId getSeasonId() {
		return seasonId;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStartsOn() {
		return startsOn;
	}

	public LocalDate getEndsOn() {
		return endsOn;
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
