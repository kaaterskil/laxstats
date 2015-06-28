package laxstats.domain.games;

import java.util.List;

import laxstats.api.games.GroundBallUpdatedEvent;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayType;

import org.axonframework.eventhandling.annotation.EventHandler;

public class GroundBall extends Play {

	public GroundBall(String id, String eventId, String teamId, int period,
			String comment, List<PlayParticipantDTO> participants) {
		super(id, PlayType.GROUND_BALL, PlayKey.PLAY, eventId, teamId, period,
				null, null, null, comment, participants);
	}

	protected GroundBall() {
		super();
	}

	@EventHandler
	protected void handle(GroundBallUpdatedEvent event) {
		if (!event.getPlayId().equals(id)) {
			return;
		}
		final PlayDTO dto = event.getPlayDTO();
		teamId = dto.getTeam().getId();
		period = dto.getPeriod();
		comment = dto.getComment();
		for (final PlayParticipant participant : participants) {
			updateParticipant(participant, dto.getParticipants());
		}
	}
}
