package laxstats.api.games;

public class CreateEventCommand extends AbstractEventCommand {
	private final GameDTO gameDTO;

	public CreateEventCommand(GameId gameId, GameDTO gameDTO) {
		super(gameId);
		this.gameDTO = gameDTO;
	}

	public GameDTO getEventDTO() {
		return gameDTO;
	}

}
