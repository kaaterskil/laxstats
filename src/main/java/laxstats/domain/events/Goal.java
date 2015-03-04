package laxstats.domain.events;

import java.util.Iterator;
import java.util.List;

import laxstats.api.events.EventId;
import laxstats.api.events.GoalUpdatedEvent;
import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayParticipantDTO;
import laxstats.api.events.PlayResult;
import laxstats.api.events.PlaySequenceNumberChangedEvent;
import laxstats.api.events.PlayTeamScoreChangedEvent;
import laxstats.api.events.PlayType;
import laxstats.api.events.ScoreAttemptType;
import laxstats.api.events.Strength;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.Period;

public class Goal extends Play {
	private int sequenceNumber;
	private int teamScore;
	private int opponentScore;
	private Strength strength;
	private int manUpAdvantage;
	private String manUpTeamId;

	public Goal(String id, String eventId, String teamId, int period,
			Period elapsedTime, ScoreAttemptType scoreAttemptType,
			String comment, int sequenceNumber, int teamScore,
			int opponentScore, Strength strength, int manUpAdvantage,
			String manUpTeamId, List<PlayParticipantDTO> participants) {
		super(id, PlayType.GOAL, PlayKey.GOAL, eventId, teamId, period,
				elapsedTime, scoreAttemptType, PlayResult.GOAL, comment,
				participants);
		this.sequenceNumber = sequenceNumber;
		this.teamScore = teamScore;
		this.opponentScore = opponentScore;
		this.strength = strength;
		this.manUpAdvantage = manUpAdvantage;
		this.manUpTeamId = manUpTeamId;
	}

	protected Goal() {
		super();
	}

	/*---------- Methods -----------*/

	public void adjustSequenceNumber(int sequenceNumber) {
		final EventId identifier = new EventId(eventId);
		apply(new PlaySequenceNumberChangedEvent(identifier, id, sequenceNumber));
	}

	public void adjustTeamScore(int teamScore) {
		final EventId identifier = new EventId(eventId);
		apply(new PlaySequenceNumberChangedEvent(identifier, id, teamScore));
	}

	/*---------- Event handlers -----------*/

	@EventHandler
	protected void handle(GoalUpdatedEvent event) {
		if (!event.getPlayId().equals(id)) {
			return;
		}
		final PlayDTO dto = event.getPlayDTO();
		teamId = dto.getTeam().getId();
		period = dto.getPeriod();
		elapsedTime = dto.getElapsedTime();
		result = dto.getResult();

		sequenceNumber = dto.getSequence();
		teamScore = dto.getTeamScore();
		opponentScore = dto.getOpponentScore();
		strength = dto.getStrength();
		manUpAdvantage = dto.getManUpAdvantage();
		manUpTeamId = dto.getManUpTeam().getId();

		// Update existing scorer and assist
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
			// Create new participant (assist)
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

	@EventHandler
	protected void handle(PlaySequenceNumberChangedEvent event) {
		if (!event.getPlayId().equals(id)) {
			return;
		}
		sequenceNumber = event.getSequenceNumber();
	}

	@EventHandler
	protected void handle(PlayTeamScoreChangedEvent event) {
		if (!event.getPlayId().equals(id)) {
			return;
		}
		teamScore = event.getTeamScore();
	}

	/*---------- Getter/Setters ----------*/

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public int getTeamScore() {
		return teamScore;
	}

	public int getOpponentScore() {
		return opponentScore;
	}

	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}

	public Strength getStrength() {
		return strength;
	}

	public void setStrength(Strength strength) {
		this.strength = strength;
	}

	public int getManUpAdvantage() {
		return manUpAdvantage;
	}

	public void setManUpAdvantage(int manUpAdvantage) {
		this.manUpAdvantage = manUpAdvantage;
	}

	public String getManUpTeamId() {
		return manUpTeamId;
	}

	public void setManUpTeamId(String manUpTeamId) {
		this.manUpTeamId = manUpTeamId;
	}
}
