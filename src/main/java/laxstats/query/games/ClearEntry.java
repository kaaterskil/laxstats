package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

@Entity
@DiscriminatorValue(PlayType.CLEAR)
public class ClearEntry extends PlayEntry {
	private static final long serialVersionUID = -1067151560295967085L;

	public ClearEntry() {
		playKey = PlayKey.PLAY;
	}
}
