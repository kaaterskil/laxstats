package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.plays.PlayKey;
import laxstats.api.plays.PlayType;

@Entity
@DiscriminatorValue(PlayType.SHOT)
public class ShotEntry extends PlayEntry {

	public ShotEntry() {
		playKey = PlayKey.PLAY;
	}
}
