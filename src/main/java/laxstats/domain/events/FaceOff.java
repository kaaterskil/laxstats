package laxstats.domain.events;

import java.util.List;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayParticipantDTO;
import laxstats.api.events.PlayType;

public class FaceOff extends Play {

	public FaceOff(String id, String eventId, String teamId, int period,
			String comment, List<PlayParticipantDTO> participants) {
		super(id, PlayType.FACEOFF, PlayKey.PLAY, eventId, teamId, period,
				null, null, null, comment, participants);
	}

	protected FaceOff() {
		super();
	}
}
