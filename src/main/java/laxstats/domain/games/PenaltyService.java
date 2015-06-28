package laxstats.domain.games;

import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayRole;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class PenaltyService extends PlayServiceImpl {

	public PenaltyService(Game game) {
		super(game);
	}

	@Override
	public boolean canRecordPlay(PlayDTO dto) {
		if (playRecorded(dto)) {
			return false;
		}
		if (!participantsRegistered(dto)) {
			return false;
		}
		if (participantsSidelined(dto)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean canUpdatePlay(PlayDTO dto) {
		if (!playRecorded(dto)) {
			return false;
		}
		if (!participantsRegistered(dto)) {
			return false;
		}
		if (participantsSidelined(dto)) {
			return false;
		}
		return true;
	}

	@Override
	protected boolean participantsSidelined(PlayDTO dto) {
		final DateTime instant = dto.getInstant();
		for (final Penalty penalty : getEvent().getPenalties()) {
			final Interval interval = penalty.getInterval();
			final boolean overlaps = interval.contains(instant);
			final boolean concurrent = interval.getStart().equals(instant);

			if (overlaps && !concurrent) {
				for (final PlayParticipantDTO pdto : dto.getParticipants()) {
					final String targetId = pdto.getAttendee().getId();

					for (final PlayParticipant participant : penalty
							.getParticipants()) {
						final String attendeeId = participant.getAttendeeId();
						final PlayRole role = participant.getRole();

						if (attendeeId.equals(targetId)
								&& role.equals(PlayRole.PENALTY_COMMITTED_BY)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
