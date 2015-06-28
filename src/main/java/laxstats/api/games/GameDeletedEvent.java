package laxstats.api.games;

public class GameDeletedEvent {
	private final GameId gameId;

	public GameDeletedEvent(GameId gameId) {
		this.gameId = gameId;
	}

	public GameId getEventId() {
		return gameId;
	}
}
