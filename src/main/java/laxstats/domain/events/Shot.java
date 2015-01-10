package laxstats.domain.events;

import java.util.List;

import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayParticipantDTO;
import laxstats.api.events.PlayResult;
import laxstats.api.events.PlayType;
import laxstats.api.events.ScoreAttemptType;
import laxstats.api.events.ShotUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.LocalTime;

public class Shot extends Play {

	public Shot(String id, String eventId, String teamId, int period,
			LocalTime elapsedTime, ScoreAttemptType scoreAttemptType,
			PlayResult result, String comment,
			List<PlayParticipantDTO> participants) {
		super(id, PlayType.SHOT, PlayKey.PLAY, eventId, teamId, period,
				elapsedTime, scoreAttemptType, result, comment, participants);
	}

	protected Shot() {
		super();
	}

	@EventHandler
	protected void handle(ShotUpdatedEvent event) {
		if (!event.getPlayId().equals(id)) {
			return;
		}

		final PlayDTO dto = event.getPlayDTO();
		teamId = dto.getTeam().getId();
		period = dto.getPeriod();
		elapsedTime = dto.getElapsedTime();
		comment = dto.getComment();

		scoreAttemptType = dto.getAttemptType();
		result = dto.getResult();
		for (final PlayParticipant participant : participants) {
			updateParticipant(participant, dto.getParticipants());
		}
	}
}
