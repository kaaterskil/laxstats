package laxstats.api.games;

public class GameUpdatedEvent {
	private final GameId gameId;
	private final GameDTO gameDTO;

	public GameUpdatedEvent(GameId gameId, GameDTO gameDTO) {
		this.gameId = gameId;
		this.gameDTO = gameDTO;
	}

	public GameId getEventId() {
		return gameId;
	}

	public GameDTO getEventDTO() {
		return gameDTO;
	}
}
