package laxstats.domain.plays;

import laxstats.api.plays.GroundBallCreatedEvent;
import laxstats.api.plays.PlayDTO;
import laxstats.api.plays.PlayId;

public class GroundBall extends Play {
	private static final long serialVersionUID = -9040960027491565354L;

	public GroundBall(PlayId playId, PlayDTO playDTO) {
		apply(new GroundBallCreatedEvent(playId, playDTO));
	}

	protected GroundBall() {
	}
}
