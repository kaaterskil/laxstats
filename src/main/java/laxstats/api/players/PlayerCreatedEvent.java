package laxstats.api.players;

public class PlayerCreatedEvent {
	private final PlayerId playerId;
	private final PlayerDTO playerDTO;

	public PlayerCreatedEvent(PlayerId playerId, PlayerDTO playerDTO) {
		this.playerId = playerId;
		this.playerDTO = playerDTO;
	}

	public PlayerId getPlayerId() {
		return playerId;
	}

	public PlayerDTO getPlayerDTO() {
		return playerDTO;
	}
}
