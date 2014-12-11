package laxstats.api.plays;

public class PenaltyTypeUpdatedEvent {

	private final PenaltyTypeId penaltyTypeId;
	private final PenaltyTypeDTO penaltyTypeDTO;

	public PenaltyTypeUpdatedEvent(PenaltyTypeId penaltyTypeId,
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
