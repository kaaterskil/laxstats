package laxstats.api.plays;

public class PenaltyTypeDeletedEvent {

	private final PenaltyTypeId penaltyTypeId;

	public PenaltyTypeDeletedEvent(PenaltyTypeId penaltyTypeId) {
		this.penaltyTypeId = penaltyTypeId;
	}

	public PenaltyTypeId getPenaltyTypeId() {
		return penaltyTypeId;
	}
}
