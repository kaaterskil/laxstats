package laxstats.api.games;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public abstract class AbstractEventCommand {
	@TargetAggregateIdentifier
	private final GameId gameId;

	protected AbstractEventCommand(GameId gameId) {
		this.gameId = gameId;
	}

	public GameId getEventId() {
		return gameId;
	}
}
