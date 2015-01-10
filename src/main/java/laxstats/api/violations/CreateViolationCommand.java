package laxstats.api.violations;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateViolationCommand {

	@TargetAggregateIdentifier
	private final ViolationId penaltyTypeId;
	private final ViolationDTO penaltyTypeDTO;

	public CreateViolationCommand(ViolationId penaltyTypeId,
			ViolationDTO penaltyTypeDTO) {
		this.penaltyTypeId = penaltyTypeId;
		this.penaltyTypeDTO = penaltyTypeDTO;
	}

	public ViolationId getPenaltyTypeId() {
		return penaltyTypeId;
	}

	public ViolationDTO getPenaltyTypeDTO() {
		return penaltyTypeDTO;
	}
}
