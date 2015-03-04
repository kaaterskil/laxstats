package laxstats.domain.events;

import java.util.List;

import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayType;

import org.joda.time.Period;

public class GoalService extends PlayServiceImpl {

	public GoalService(Event event) {
		super(event);
	}

	@Override
	public boolean playRecorded(PlayDTO dto) {
		final String key = dto.getIdentifier().toString();
		if (getEvent().getPlays().containsKey(key)) {
			return true;
		}
		for (final Play each : getPlays(null)) {
			if (each.getDiscriminator().equals(dto.getDiscriminator())
					&& each.getPlayKey().equals(dto.getPlayKey())
					&& each.getTeamId().equals(dto.getTeam().getId())
					&& each.getPeriod() == dto.getPeriod()
					&& each.getElapsedTime().equals(dto.getElapsedTime())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setInvariants(PlayDTO dto) {
		final List<Play> goals = getPlays(PlayType.GOAL);
		final Period elapsedTime = dto.getTotalElapsedTime();
		final String teamId = dto.getTeam().getId();
		boolean prepended = false;
		boolean sequenceSet = false;
		boolean scoreSet = false;

		int sequence = 0;
		int teamScore = 0;
		int opponentScore = 0;
		for (final Play each : goals) {
			final Goal goal = (Goal) each;

			// For 1-based values
			if (goal.getTeamId().equals(teamId)) {
				teamScore++;
			} else {
				opponentScore++;
			}

			// For goals inserted into an existing sequence
			if (goal.getTotalElapsedTime().toStandardSeconds().getSeconds() > elapsedTime
					.toStandardSeconds().getSeconds()) {
				prepended = true;

				// Set sequence number
				if (!sequenceSet) {
					dto.setSequence(sequence++);
					sequenceSet = true;
				}
				goal.adjustSequenceNumber(sequence);

				// Set team score
				if (goal.getTeamId().equals(teamId)) {
					if (!scoreSet) {
						dto.setTeamScore(teamScore++);
						dto.setOpponentScore(opponentScore);
						scoreSet = true;
					}
					goal.adjustTeamScore(teamScore);
				}
			}

			// For 0-based values
			sequence++;
		}
		// For goals appended to an existing sequence
		if (!prepended) {
			dto.setSequence(sequence);
			dto.setTeamScore(teamScore + 1);
			dto.setOpponentScore(opponentScore);
		}
	}
}
