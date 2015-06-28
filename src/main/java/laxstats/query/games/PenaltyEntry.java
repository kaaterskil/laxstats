package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayRole;
import laxstats.api.games.PlayType;
import laxstats.api.games.PlayUtils;

import org.joda.time.Interval;

@Entity
@DiscriminatorValue(PlayType.PENALTY)
public class PenaltyEntry extends PlayEntry {
	private static final long serialVersionUID = -8712295107965966601L;

	public PenaltyEntry() {
		playKey = PlayKey.PLAY;
	}

	public Interval getInterval() {
		return PlayUtils.getPenaltyInterval(event.getStartsAt(), period,
				elapsedTime, duration);
	}

	public PlayParticipantEntry getCommittedBy() {
		PlayParticipantEntry result = null;
		for (final PlayParticipantEntry player : participants) {
			if (player.getRole().equals(PlayRole.PENALTY_COMMITTED_BY)) {
				result = player;
			}
		}
		return result;
	}

	public PlayParticipantEntry getCommittedAgainst() {
		PlayParticipantEntry result = null;
		for (final PlayParticipantEntry player : participants) {
			if (player.getRole().equals(PlayRole.PENALTY_COMMITTED_AGAINST)) {
				result = player;
			}
		}
		return result;
	}
}
