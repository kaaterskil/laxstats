package laxstats.api.violations;

public class ViolationDeletedEvent {

	private final ViolationId penaltyTypeId;

	public ViolationDeletedEvent(ViolationId penaltyTypeId) {
		this.penaltyTypeId = penaltyTypeId;
	}

	public ViolationId getPenaltyTypeId() {
		return penaltyTypeId;
	}
}
