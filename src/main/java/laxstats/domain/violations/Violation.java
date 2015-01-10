package laxstats.domain.violations;

import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;
import laxstats.api.violations.ViolationCreatedEvent;
import laxstats.api.violations.ViolationDTO;
import laxstats.api.violations.ViolationDeletedEvent;
import laxstats.api.violations.ViolationId;
import laxstats.api.violations.ViolationUpdatedEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Violation extends AbstractAnnotatedAggregateRoot<ViolationId> {
	private static final long serialVersionUID = -132099412870035181L;

	@AggregateIdentifier
	private ViolationId violationId;
	private String name;
	private String description;
	private PenaltyCategory category;
	private PenaltyLength duration;
	private boolean releasable;

	public Violation(ViolationId violationId, ViolationDTO violationDTO) {
		apply(new ViolationCreatedEvent(violationId, violationDTO));
	}

	protected Violation() {
	}

	// ---------- Methods ----------//

	public void update(ViolationId violationId, ViolationDTO violationDTO) {
		apply(new ViolationUpdatedEvent(violationId, violationDTO));
	}

	public void delete(ViolationId violationId) {
		apply(new ViolationDeletedEvent(violationId));
	}

	// ---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(ViolationCreatedEvent event) {
		final ViolationDTO dto = event.getPenaltyTypeDTO();

		violationId = event.getPenaltyTypeId();
		name = dto.getName();
		description = dto.getDescription();
		category = dto.getCategory();
		duration = dto.getPenaltyLength();
		releasable = dto.isReleasable();
	}

	@EventSourcingHandler
	protected void handle(ViolationUpdatedEvent event) {
		final ViolationDTO dto = event.getPenaltyTypeDTO();

		name = dto.getName();
		description = dto.getDescription();
		category = dto.getCategory();
		duration = dto.getPenaltyLength();
		releasable = dto.isReleasable();
	}

	@EventSourcingHandler
	protected void handle(ViolationDeletedEvent event) {
		markDeleted();
	}

	// ---------- Getters ----------//

	@Override
	public ViolationId getIdentifier() {
		return violationId;
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
