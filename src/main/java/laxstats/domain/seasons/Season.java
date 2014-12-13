package laxstats.domain.seasons;

import laxstats.api.seasons.*;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDate;

public class Season extends AbstractAnnotatedAggregateRoot<SeasonId> {
	private static final long serialVersionUID = 8223916312274072503L;

	@AggregateIdentifier
	private SeasonId seasonId;	
	private String description;
	private LocalDate startsOn;
	private LocalDate endsOn;

	public Season(SeasonId seasonId, SeasonDTO seasonDTO) {
		apply(new SeasonCreatedEvent(seasonId, seasonDTO));
	}

	protected Season() {
	}

	//---------- Methods ----------//

	public void update(SeasonId seasonId, SeasonDTO seasonDTO) {
		apply(new SeasonUpdatedEvent(seasonId, seasonDTO));
	}

	public void delete(SeasonId seasonId) {
		apply(new SeasonDeletedEvent(seasonId));
	}

	//---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(SeasonCreatedEvent event) {
		SeasonDTO dto = event.getSeasonDTO();
		seasonId = event.getSeasonId();
		description = dto.getDescription();
		startsOn = dto.getStartsOn();
		endsOn = dto.getEndsOn();
	}

	@EventSourcingHandler
	protected void handle(SeasonUpdatedEvent event) {
		SeasonDTO dto = event.getSeasonDTO();
		description = dto.getDescription();
		startsOn = dto.getStartsOn();
		endsOn = dto.getEndsOn();
	}

	@EventSourcingHandler
	protected void handle(SeasonDeletedEvent event) {
		markDeleted();
	}

	//---------- Getters ----------//

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
}
