package laxstats.api.plays;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreatePenaltyTypeCommand {

	@TargetAggregateIdentifier
	private final PenaltyTypeId penaltyTypeId;
	private final PenaltyTypeDTO penaltyTypeDTO;

	public CreatePenaltyTypeCommand(PenaltyTypeId penaltyTypeId,
			PenaltyTypeDTO penaltyTypeDTO) {
		this.penaltyTypeId = penaltyTypeId;
		this.penaltyTypeDTO = penaltyTypeDTO;
	}

	public PenaltyTypeId getPenaltyTypeId() {
		return penaltyTypeId;
	}

	public PenaltyTypeDTO getPenaltyTypeDTO() {
		return penaltyTypeDTO;
	}
}
