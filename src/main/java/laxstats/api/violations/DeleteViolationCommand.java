package laxstats.api.violations;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DeleteViolationCommand {

	@TargetAggregateIdentifier
	private final ViolationId penaltyTypeId;

	public DeleteViolationCommand(ViolationId penaltyTypeId) {
		this.penaltyTypeId = penaltyTypeId;
	}

	public ViolationId getPenaltyTypeId() {
		return penaltyTypeId;
	}
}
