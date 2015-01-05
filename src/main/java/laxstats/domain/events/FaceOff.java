package laxstats.domain.events;

import java.util.List;

import laxstats.api.events.FaceOffUpdatedEvent;
import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayParticipantDTO;
import laxstats.api.events.PlayType;

import org.axonframework.eventhandling.annotation.EventHandler;

public class FaceOff extends Play {

	public FaceOff(String id, String eventId, String teamId, int period,
			String comment, List<PlayParticipantDTO> participants) {
		super(id, PlayType.FACEOFF, PlayKey.PLAY, eventId, teamId, period,
				null, null, null, comment, participants);
	}

	protected FaceOff() {
		super();
	}

	@EventHandler
	protected void handle(FaceOffUpdatedEvent event) {
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
