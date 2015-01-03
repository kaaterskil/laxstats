package laxstats.domain.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayParticipantDTO;

import org.joda.time.LocalTime;

public class PlayServiceImpl implements PlayService {
	private final Event event;

	protected PlayServiceImpl(Event event) {
		this.event = event;
	}

	@Override
	public final Event getEvent() {
		return event;
	}

	@Override
	public List<Play> getPlays(String discriminator) {
		final List<Play> list = new ArrayList<>();
		for (final Play each : event.getPlays().values()) {
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

	private static class ElapsedTimeComparator implements Comparator<Play> {
		@Override
		public int compare(Play o1, Play o2) {
			final LocalTime t1 = o1.getTotalElapsedTime();
			final LocalTime t2 = o2.getTotalElapsedTime();
			return t1.isBefore(t2) ? -1 : (t1.isAfter(t2) ? 1 : 0);
		}
	}

}
