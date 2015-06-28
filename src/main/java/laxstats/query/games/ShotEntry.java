package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

@Entity
@DiscriminatorValue(PlayType.SHOT)
public class ShotEntry extends PlayEntry {
	private static final long serialVersionUID = -6788526267404351022L;

	public ShotEntry() {
		playKey = PlayKey.PLAY;
	}
}
