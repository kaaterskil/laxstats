package laxstats.api.violations;

public class ViolationCreatedEvent {

	private final ViolationId penaltyTypeId;
	private final ViolationDTO penaltyTypeDTO;

	public ViolationCreatedEvent(ViolationId penaltyTypeId,
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
