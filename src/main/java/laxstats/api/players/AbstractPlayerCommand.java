package laxstats.api.players;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class AbstractPlayerCommand {
	@TargetAggregateIdentifier
	private final PlayerId playerId;

	protected AbstractPlayerCommand(PlayerId playerId) {
		this.playerId = playerId;
	}

	public PlayerId getPlayerId() {
		return playerId;
	}
}
