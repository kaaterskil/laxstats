package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.plays.PlayKey;
import laxstats.api.plays.PlayType;

@Entity
@DiscriminatorValue(PlayType.PENALTY)
public class PenaltyEntry extends PlayEntry {

	public PenaltyEntry() {
		playKey = PlayKey.PLAY;
	}
}
