package laxstats.api.players;

public class UpdatePlayerCommand extends AbstractPlayerCommand {
	private final PlayerDTO playerDTO;

	public UpdatePlayerCommand(PlayerId playerId, PlayerDTO playerDTO) {
		super(playerId);
		this.playerDTO = playerDTO;
	}

	public PlayerDTO getPlayerDTO() {
		return playerDTO;
	}
}
