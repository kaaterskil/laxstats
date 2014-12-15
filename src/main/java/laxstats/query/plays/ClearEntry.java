package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.plays.PlayKey;
import laxstats.api.plays.PlayType;

@Entity
@DiscriminatorValue(PlayType.CLEAR)
public class ClearEntry extends PlayEntry {

	public ClearEntry() {
		playKey = PlayKey.PLAY;
	}
}
