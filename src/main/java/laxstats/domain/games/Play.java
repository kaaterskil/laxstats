package laxstats.domain.games;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayUtils;
import laxstats.api.games.ScoreAttemptType;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.joda.time.Period;

public abstract class Play extends AbstractAnnotatedEntity {
	protected String id;
	protected String discriminator;
	protected PlayKey playKey;
	protected String eventId;
	protected String teamId;
	protected int period;
	protected Period elapsedTime;
	protected ScoreAttemptType scoreAttemptType;
	protected PlayResult result;
	protected String comment;

	@EventSourcedMember
	protected final List<PlayParticipant> participants = new ArrayList<>();

	protected Play(String id, String discriminator, PlayKey playKey,
			String eventId, String teamId, int period, Period elapsedTime,
			ScoreAttemptType scoreAttemptType, PlayResult result,
			String comment, List<PlayParticipantDTO> participants) {
		this.id = id;
		this.discriminator = discriminator;
		this.playKey = playKey;
		this.eventId = eventId;
		this.teamId = teamId;
		this.period = period;
		this.elapsedTime = elapsedTime;
		this.scoreAttemptType = scoreAttemptType;
		this.result = result;
		this.comment = comment;

		if (participants != null) {
			for (final PlayParticipantDTO dto : participants) {
				final String attendeeId = dto.getAttendee().getId();
				final PlayParticipant entity = new PlayParticipant(dto.getId(),
						id, attendeeId, teamId, dto.getRole(),
						dto.isPointCredit(), dto.getCumulativeAssists(),
						dto.getCumulativeGoals());
				this.participants.add(entity);
			}
		}
	}

	protected Play() {
	}

	/*---------- Methods ----------*/

	public Period getTotalElapsedTime() {
		return PlayUtils.getTotalElapsedTime(period, elapsedTime);
	}

	protected void updateParticipant(PlayParticipant participant,
			List<PlayParticipantDTO> list) {
		for (final PlayParticipantDTO dto : list) {
			if (dto.getRole().equals(participant.getRole())) {
				participant.update(dto);
			}
		}
	}

	/*---------- Getters ----------*/

	public String getId() {
		return id;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public PlayKey getPlayKey() {
		return playKey;
	}

	public String getEventId() {
		return eventId;
	}

	public String getTeamId() {
		return teamId;
	}

	public int getPeriod() {
		return period;
	}

	public Period getElapsedTime() {
		return elapsedTime;
	}

	public ScoreAttemptType getScoreAttemptType() {
		return scoreAttemptType;
	}

	public PlayResult getResult() {
		return result;
	}

	public String getComment() {
		return comment;
	}

	public List<PlayParticipant> getParticipants() {
		return participants;
	}
}
