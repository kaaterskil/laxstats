package laxstats.domain.events;

import java.util.List;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayParticipantDTO;
import laxstats.api.events.PlayType;

public class GroundBall extends Play {

	public GroundBall(String id, String eventId, String teamId, int period,
			String comment, List<PlayParticipantDTO> participants) {
		super(id, PlayType.GROUND_BALL, PlayKey.PLAY, eventId, teamId, period,
				null, null, null, comment, participants);
	}

	protected GroundBall() {
		super();
	}
}
