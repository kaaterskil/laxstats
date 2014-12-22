package laxstats.domain.plays;

import laxstats.api.plays.GoalCreatedEvent;
import laxstats.api.plays.PlayDTO;
import laxstats.api.plays.PlayId;

public class Goal extends Play {
	private static final long serialVersionUID = -3251401197985017082L;

	public Goal(PlayId playId, PlayDTO playDTO) {
		apply(new GoalCreatedEvent(playId, playDTO));
	}

	protected Goal() {
	}
}
