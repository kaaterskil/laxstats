package laxstats.api.plays;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdatePenaltyTypeCommand {

	@TargetAggregateIdentifier
	private final PenaltyTypeId penaltyTypeId;
	private final PenaltyTypeDTO penaltyTypeDTO;

	public UpdatePenaltyTypeCommand(PenaltyTypeId penaltyTypeId,
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
