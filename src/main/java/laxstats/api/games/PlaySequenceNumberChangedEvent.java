package laxstats.api.games;

public class PlaySequenceNumberChangedEvent {
	private final GameId gameId;
	private final String playId;
	private final int sequenceNumber;

	public PlaySequenceNumberChangedEvent(GameId gameId, String playId,
			int sequenceNumber) {
		this.gameId = gameId;
		this.playId = playId;
		this.sequenceNumber = sequenceNumber;
	}

	public GameId getEventId() {
		return gameId;
	}

	public String getPlayId() {
		return playId;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}
}
