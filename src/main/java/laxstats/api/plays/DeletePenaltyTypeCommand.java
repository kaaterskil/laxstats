package laxstats.api.plays;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DeletePenaltyTypeCommand {

	@TargetAggregateIdentifier
	private final PenaltyTypeId penaltyTypeId;

	public DeletePenaltyTypeCommand(PenaltyTypeId penaltyTypeId) {
		this.penaltyTypeId = penaltyTypeId;
	}

	public PenaltyTypeId getPenaltyTypeId() {
		return penaltyTypeId;
	}
}
