package laxstats.domain.plays;

import laxstats.api.plays.PenaltyCategory;
import laxstats.api.plays.PenaltyLength;
import laxstats.api.plays.PenaltyTypeCreatedEvent;
import laxstats.api.plays.PenaltyTypeDTO;
import laxstats.api.plays.PenaltyTypeDeletedEvent;
import laxstats.api.plays.PenaltyTypeId;
import laxstats.api.plays.PenaltyTypeUpdatedEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class PenaltyType extends AbstractAnnotatedAggregateRoot<PenaltyTypeId> {
	private static final long serialVersionUID = -132099412870035181L;

	@AggregateIdentifier
	private PenaltyTypeId penaltyTypeId;
	private String name;
	private String description;
	private PenaltyCategory category;
	private PenaltyLength duration;
	private boolean releasable;

	public PenaltyType(PenaltyTypeId penaltyTypeId,
			PenaltyTypeDTO penaltyTypeDTO) {
		apply(new PenaltyTypeCreatedEvent(penaltyTypeId, penaltyTypeDTO));
	}

	protected PenaltyType() {
	}

	// ---------- Methods ----------//

	public void update(PenaltyTypeId penaltyTypeId,
			PenaltyTypeDTO penaltyTypeDTO) {
		apply(new PenaltyTypeUpdatedEvent(penaltyTypeId, penaltyTypeDTO));
	}

	public void delete(PenaltyTypeId penaltyTypeId) {
		apply(new PenaltyTypeDeletedEvent(penaltyTypeId));
	}

	// ---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(PenaltyTypeCreatedEvent event) {
		final PenaltyTypeDTO dto = event.getPenaltyTypeDTO();

		penaltyTypeId = event.getPenaltyTypeId();
		name = dto.getName();
		description = dto.getDescription();
		category = dto.getCategory();
		duration = dto.getPenaltyLength();
		releasable = dto.isReleasable();
	}

	@EventSourcingHandler
	protected void handle(PenaltyTypeUpdatedEvent event) {
		final PenaltyTypeDTO dto = event.getPenaltyTypeDTO();

		name = dto.getName();
		description = dto.getDescription();
		category = dto.getCategory();
		duration = dto.getPenaltyLength();
		releasable = dto.isReleasable();
	}

	@EventSourcingHandler
	protected void handle(PenaltyTypeDeletedEvent event) {
		markDeleted();
	}

	// ---------- Getters ----------//

	@Override
	public PenaltyTypeId getIdentifier() {
		return penaltyTypeId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public PenaltyCategory getCategory() {
		return category;
	}

	public PenaltyLength getDuration() {
		return duration;
	}

	public boolean isReleasable() {
		return releasable;
	}
}
