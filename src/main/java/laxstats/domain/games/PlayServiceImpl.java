package laxstats.domain.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayRole;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.PenaltyEntry;
import laxstats.query.games.PlayParticipantEntry;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Seconds;

public class PlayServiceImpl implements PlayService {
	private final Game game;

	protected PlayServiceImpl(Game game) {
		this.game = game;
	}

	@Override
	public final Game getEvent() {
		return game;
	}

	@Override
	public List<Play> getPlays(String discriminator) {
		final List<Play> list = new ArrayList<>();
		for (final Play each : game.getPlays().values()) {
			if (discriminator == null
					|| each.getDiscriminator().equals(discriminator)) {
				list.add(each);
			}
		}
		Collections.sort(list, new ElapsedTimeComparator());
		return list;
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
	public boolean playRecorded(PlayDTO dto) {
		final String key = dto.getIdentifier().toString();
		return getEvent().getPlays().containsKey(key);
	}

	@Override
	public boolean participantsRegistered(PlayDTO dto) {
		for (final PlayParticipantDTO participantDTO : dto.getParticipants()) {
			if (!participantRegistered(participantDTO)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void setInvariants(PlayDTO dto) {
		// Noop
	}

	protected final boolean participantRegistered(PlayParticipantDTO dto) {
		final String attendeeId = dto.getAttendee().getId();
		return getEvent().getAttendees().containsKey(attendeeId);
	}

	protected boolean participantsSidelined(PlayDTO dto) {
		final DateTime instant = dto.getInstant();
		for (final PenaltyEntry penalty : dto.getEvent().getPenalties()) {
			final Interval interval = penalty.getInterval();
			final boolean overlaps = interval.contains(instant);
			final boolean concurrent = interval.getStart().equals(instant);

			if (overlaps && !concurrent) {
				for (final PlayParticipantDTO pdto : dto.getParticipants()) {
					final AttendeeEntry target = pdto.getAttendee();

					for (final PlayParticipantEntry p : penalty
							.getParticipants()) {
						final AttendeeEntry attendee = p.getAttendee();
						final PlayRole role = p.getRole();

						if (attendee.equals(target)
								&& role.equals(PlayRole.PENALTY_COMMITTED_BY)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static class ElapsedTimeComparator implements Comparator<Play> {
		@Override
		public int compare(Play o1, Play o2) {
			final Seconds t1 = o1.getTotalElapsedTime().toStandardSeconds();
			final Seconds t2 = o2.getTotalElapsedTime().toStandardSeconds();
			return t1.getSeconds() < t2.getSeconds() ? -1
					: (t1.getSeconds() > t2.getSeconds() ? 1 : 0);
		}
	}

}
