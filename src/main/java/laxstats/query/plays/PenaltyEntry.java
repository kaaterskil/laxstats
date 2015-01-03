package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.PENALTY)
public class PenaltyEntry extends PlayEntry {

	public PenaltyEntry() {
		playKey = PlayKey.PLAY;
	}
}
