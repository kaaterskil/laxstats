package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.GROUND_BALL)
public class GroundBallEntry extends PlayEntry {

	public GroundBallEntry() {
		playKey = PlayKey.PLAY;
	}
}
