package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

@Entity
@DiscriminatorValue(PlayType.GROUND_BALL)
public class GroundBallEntry extends PlayEntry {
	private static final long serialVersionUID = 4394861925132487037L;

	public GroundBallEntry() {
		playKey = PlayKey.PLAY;
	}
}
