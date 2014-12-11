package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.plays.PlayKey;

@Entity
@DiscriminatorValue("CLEAR")
public class ClearEntry extends PlayEntry {

	public ClearEntry() {
		playKey = PlayKey.PLAY;
	}
}
