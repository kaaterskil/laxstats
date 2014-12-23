package laxstats.api.players;

public class PlayerDeletedEvent {
	private final PlayerId playerId;

	public PlayerDeletedEvent(PlayerId playerId) {
		this.playerId = playerId;
	}

	public PlayerId getPlayerId() {
		return playerId;
	}

}
