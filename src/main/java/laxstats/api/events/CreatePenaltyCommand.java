package laxstats.api.events;

import laxstats.api.plays.PlayId;

public class CreatePenaltyCommand extends AbstractPlayCommand {
	private final PlayDTO playDTO;

	public CreatePenaltyCommand(EventId eventId, PlayId playId, PlayDTO playDTO) {
		super(eventId, playId);
		this.playDTO = playDTO;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
