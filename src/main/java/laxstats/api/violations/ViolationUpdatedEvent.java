package laxstats.api.violations;

public class ViolationUpdatedEvent {

	private final ViolationId penaltyTypeId;
	private final ViolationDTO penaltyTypeDTO;

	public ViolationUpdatedEvent(ViolationId penaltyTypeId,
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
