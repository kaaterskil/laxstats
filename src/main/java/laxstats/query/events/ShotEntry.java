package laxstats.query.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.SHOT)
public class ShotEntry extends PlayEntry {
	private static final long serialVersionUID = -6788526267404351022L;

	public ShotEntry() {
		playKey = PlayKey.PLAY;
	}
}
