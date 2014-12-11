package laxstats.api.plays;

public class PenaltyTypeCreatedEvent {

	private final PenaltyTypeId penaltyTypeId;
	private final PenaltyTypeDTO penaltyTypeDTO;

	public PenaltyTypeCreatedEvent(PenaltyTypeId penaltyTypeId,
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
