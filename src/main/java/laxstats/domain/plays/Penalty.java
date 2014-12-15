package laxstats.domain.plays;

import laxstats.api.plays.PenaltyCreatedEvent;
import laxstats.api.plays.PlayDTO;
import laxstats.api.plays.PlayId;

public class Penalty extends Play {
	private static final long serialVersionUID = 4178212479561142960L;

	public Penalty(PlayId playId, PlayDTO playDTO) {
		apply(new PenaltyCreatedEvent(playId, playDTO));
	}
}
