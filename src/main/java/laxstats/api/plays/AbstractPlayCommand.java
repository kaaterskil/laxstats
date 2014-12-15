package laxstats.api.plays;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

abstract public class AbstractPlayCommand {
	@TargetAggregateIdentifier
	protected final PlayId playId;

	protected AbstractPlayCommand(PlayId playId) {
		this.playId = playId;
	}

	public PlayId getPlayId() {
		return playId;
	}
}
