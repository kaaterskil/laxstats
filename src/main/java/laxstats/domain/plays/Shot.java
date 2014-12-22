package laxstats.domain.plays;

import laxstats.api.plays.PlayDTO;
import laxstats.api.plays.PlayId;
import laxstats.api.plays.ShotCreatedEvent;

public class Shot extends Play {
	private static final long serialVersionUID = -2695456941078586011L;

	public Shot(PlayId playId, PlayDTO playDTO) {
		apply(new ShotCreatedEvent(playId, playDTO));
	}

	protected Shot() {
	}
}
