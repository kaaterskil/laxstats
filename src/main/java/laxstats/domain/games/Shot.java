package laxstats.domain.games;

import java.util.List;

import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;
import laxstats.api.games.ShotUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.Period;

public class Shot extends Play {

	public Shot(String id, String eventId, String teamId, int period,
			Period elapsedTime, ScoreAttemptType scoreAttemptType,
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
