package laxstats.api.games;

public class GameCreatedEvent {
	private final GameId gameId;
	private final GameDTO gameDTO;

	public GameCreatedEvent(GameId gameId, GameDTO gameDTO) {
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
