package laxstats.api.games;


public abstract class AbstractPlayEvent {
	protected GameId gameId;
	protected final String playId;
	protected final PlayDTO playDTO;

	protected AbstractPlayEvent(GameId gameId, String playId, PlayDTO playDTO) {
		this.gameId = gameId;
		this.playId = playId;
		this.playDTO = playDTO;
	}

	public GameId getEventId() {
		return gameId;
	}

	public String getPlayId() {
		return playId;
	}

	public PlayDTO getPlayDTO() {
		return playDTO;
	}
}
