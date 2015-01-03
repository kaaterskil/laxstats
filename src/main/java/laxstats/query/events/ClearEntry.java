package laxstats.query.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.CLEAR)
public class ClearEntry extends PlayEntry {

	public ClearEntry() {
		playKey = PlayKey.PLAY;
	}
}
