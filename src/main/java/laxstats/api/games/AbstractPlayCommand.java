package laxstats.api.games;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

abstract public class AbstractPlayCommand {
	@TargetAggregateIdentifier
	protected GameId gameId;
	protected String playId;

	protected AbstractPlayCommand(GameId gameId, String playId) {
		this.gameId = gameId;
		this.playId = playId;
	}

	public GameId getEventId() {
		return gameId;
	}

	public String getPlayId() {
		return playId;
	}
}
