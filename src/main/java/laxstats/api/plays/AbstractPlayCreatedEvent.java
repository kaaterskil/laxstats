package laxstats.api.plays;

public abstract class AbstractPlayCreatedEvent {
	protected final PlayId playId;
	protected final PlayDTO playDTO;

	protected AbstractPlayCreatedEvent(PlayId playId, PlayDTO playDTO) {
		this.playId = playId;
		this.playDTO = playDTO;
	}

	public PlayId getPlayId() {
		return playId;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
