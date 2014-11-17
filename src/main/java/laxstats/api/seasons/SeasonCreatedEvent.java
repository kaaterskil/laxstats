package laxstats.api.seasons;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class SeasonCreatedEvent {
	private final SeasonId seasonId;
	private final String description;
	private final LocalDate startsOn;
	private final LocalDate endsOn;
	private final String createdBy;
	private final LocalDateTime createdAt;
	private final String modifiedBy;
	private final LocalDateTime modifiedAt;
	
	public SeasonCreatedEvent(SeasonId seasonId, String description,
			LocalDate startsOn, LocalDate endsOn, String createdBy, 
			LocalDateTime createdAt, String modifiedBy, LocalDateTime modifiedAt) {
		this.seasonId = seasonId;
		this.description = description;
		this.startsOn = startsOn;
		this.endsOn = endsOn;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.modifiedBy = modifiedBy;
		this.modifiedAt = modifiedAt;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}
}
