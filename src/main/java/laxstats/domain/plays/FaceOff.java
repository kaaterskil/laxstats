package laxstats.domain.plays;

import laxstats.api.plays.FaceOffCreatedEvent;
import laxstats.api.plays.PlayDTO;
import laxstats.api.plays.PlayId;

public class FaceOff extends Play {
	private static final long serialVersionUID = -6758026818135437361L;

	public FaceOff(PlayId playId, PlayDTO playDTO) {
		apply(new FaceOffCreatedEvent(playId, playDTO));
	}
}
