package laxstats.api.games;

public class UpdateEventCommand extends AbstractEventCommand {
	private final GameDTO gameDTO;

	public UpdateEventCommand(GameId gameId, GameDTO gameDTO) {
		super(gameId);
		this.gameDTO = gameDTO;
	}

	public GameDTO getEventDTO() {
		return gameDTO;
	}

}
