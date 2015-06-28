package laxstats.domain.games;

import laxstats.api.games.ClearUpdatedEvent;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;

import org.axonframework.eventhandling.annotation.EventHandler;

public class Clear extends Play {

	public Clear(String id, String eventId, String teamId, int period,
			PlayResult result, String comment) {
		super(id, PlayType.CLEAR, PlayKey.PLAY, eventId, teamId, period, null,
				null, result, comment, null);
	}

	protected Clear() {
		super();
	}

	@EventHandler
	protected void handle(ClearUpdatedEvent event) {
		if (!event.getPlayId().equals(id)) {
			return;
		}
		final PlayDTO dto = event.getPlayDTO();
		teamId = dto.getTeam().getId();
		period = dto.getPeriod();
		result = dto.getResult();
		comment = dto.getComment();
	}
}
