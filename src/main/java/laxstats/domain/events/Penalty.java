package laxstats.domain.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laxstats.api.events.PenaltyUpdatedEvent;
import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayParticipantDTO;
import laxstats.api.events.PlayType;
import laxstats.api.events.PlayUtils;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class Penalty extends Play {
	private LocalDateTime eventStart;
	private String violationId;
	private int duration;

	public Penalty(String id, String eventId, String teamId,
			LocalDateTime eventStart, int period, LocalTime elapsedTime,
			String violationId, int duration, String comment,
			List<PlayParticipantDTO> participants) {
		super(id, PlayType.PENALTY, PlayKey.PLAY, eventId, teamId, period,
				elapsedTime, null, null, comment, participants);
		this.eventStart = eventStart;
		this.violationId = violationId;
		this.duration = duration;
	}

	public Penalty() {
		super();
	}

	/*---------- Methods ----------*/

	public List<String> getParticipantIds() {
		final List<String> list = new ArrayList<>();
		for (final PlayParticipant participant : participants) {
			list.add(participant.getAttendeeId());
		}
		return list;
	}

	public Interval getInterval() {
		return PlayUtils.getPenaltyInterval(eventStart, period, elapsedTime,
				duration);
	}

	@EventHandler
	protected void handle(PenaltyUpdatedEvent event) {
		if (!event.getPlayId().equals(id)) {
			return;
		}

		final PlayDTO dto = event.getPlayDTO();
		teamId = dto.getTeam().getId();
		period = dto.getPeriod();
		elapsedTime = dto.getElapsedTime();
		comment = dto.getComment();

		eventStart = dto.getEvent().getStartsAt();
		violationId = dto.getViolation().getId();
		duration = dto.getPenaltyDuration();

		// Update existing committedBy and committedAgainst
		updateParticipants(dto);
	}

	private void updateParticipants(PlayDTO dto) {
		// FIrst remove any deleted participants
		removeParticipant(dto);

		for (final PlayParticipantDTO pdto : dto.getParticipants()) {
			// Update existing participants
			boolean found = false;
			for (final PlayParticipant p : getParticipants()) {
				if (p.getId().equals(pdto.getId())) {
					found = true;
					p.update(pdto);
				}
			}
			// Create new participant (penalty against)
			if (!found) {
				final PlayParticipant entity = new PlayParticipant(
						pdto.getId(), id, pdto.getAttendee().getId(), pdto
								.getTeamSeason().getId(), pdto.getRole(),
						pdto.isPointCredit(), pdto.getCumulativeAssists(),
						pdto.getCumulativeGoals());
				participants.add(entity);
			}
		}
	}

	private void removeParticipant(PlayDTO dto) {
		final Iterator<PlayParticipant> iter = getParticipants().iterator();
		while (iter.hasNext()) {
			final PlayParticipant p = iter.next();
			boolean found = false;
			for (final PlayParticipantDTO pdto : dto.getParticipants()) {
				if (p.getId().equals(pdto.getId())) {
					found = true;
				}
			}
			if (!found) {
				iter.remove();
			}
		}
	}

	/*---------- Getters ----------*/

	public String getViolationId() {
		return violationId;
	}

	public int getDuration() {
		return duration;
	}

}
