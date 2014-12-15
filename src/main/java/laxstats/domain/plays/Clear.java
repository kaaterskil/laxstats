package laxstats.domain.plays;

import laxstats.api.plays.ClearCreatedEvent;
import laxstats.api.plays.PlayDTO;
import laxstats.api.plays.PlayId;

public class Clear extends Play {
	private static final long serialVersionUID = -1203947737653700482L;

	public Clear(PlayId playId, PlayDTO playDTO) {
		apply(new ClearCreatedEvent(playId, playDTO));
	}
}
