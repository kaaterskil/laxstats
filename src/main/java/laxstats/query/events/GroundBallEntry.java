package laxstats.query.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.GROUND_BALL)
public class GroundBallEntry extends PlayEntry {
	private static final long serialVersionUID = 4394861925132487037L;

	public GroundBallEntry() {
		playKey = PlayKey.PLAY;
	}
}
