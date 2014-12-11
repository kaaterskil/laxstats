package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.plays.PlayKey;

@Entity
@DiscriminatorValue("GROUND_BALL")
public class GroundBallEntry extends PlayEntry {

	public GroundBallEntry() {
		playKey = PlayKey.PLAY;
	}
}
