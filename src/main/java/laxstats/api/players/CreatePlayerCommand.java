package laxstats.api.players;

public class CreatePlayerCommand extends AbstractPlayerCommand {
	private final PlayerDTO playerDTO;

	public CreatePlayerCommand(PlayerId playerId, PlayerDTO playerDTO) {
		super(playerId);
		this.playerDTO = playerDTO;
	}

	public PlayerDTO getPlayerDTO() {
		return playerDTO;
	}
}
