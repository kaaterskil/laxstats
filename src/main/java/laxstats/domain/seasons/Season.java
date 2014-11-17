package laxstats.domain.seasons;

import laxstats.api.seasons.SeasonCreatedEvent;
import laxstats.api.seasons.SeasonId;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class Season extends AbstractAnnotatedAggregateRoot<SeasonId> {
	private static final long serialVersionUID = 8223916312274072503L;

	@AggregateIdentifier
	private SeasonId seasonId;	
	private String description;
	private LocalDate startsOn;
	private LocalDate endsOn;
	private String createdBy;
	private LocalDateTime createdAt;
	private String modifiedBy;
	private LocalDateTime modifiedAt;

	protected Season() {
	}

	public Season(SeasonId seasonId, String description, LocalDate startsOn,
			LocalDate endsOn, String createdBy, LocalDateTime createdAt,
			String modifiedBy, LocalDateTime modifiedAt) {
		apply(new SeasonCreatedEvent(seasonId, description, startsOn,
				endsOn, createdBy, createdAt, modifiedBy, modifiedAt));
	}

	@Override
	public SeasonId getIdentifier() {
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

	@EventHandler
	public void on(SeasonCreatedEvent event) {
		this.seasonId = event.getSeasonId();
		this.description = event.getDescription();
		this.startsOn = event.getStartsOn();
		this.endsOn = event.getEndsOn();
		this.createdBy = event.getCreatedBy();
		this.createdAt = event.getCreatedAt();
		this.modifiedBy = event.getModifiedBy();
		this.modifiedAt = event.getModifiedAt();
	}
}
