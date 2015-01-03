package laxstats.domain.events;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayResult;
import laxstats.api.events.PlayType;

public class Clear extends Play {

	public Clear(String id, String eventId, String teamId, int period,
			PlayResult result, String comment) {
		super(id, PlayType.CLEAR, PlayKey.PLAY, eventId, teamId, period, null,
				null, result, comment, null);
	}

	protected Clear() {
		super();
	}
}
