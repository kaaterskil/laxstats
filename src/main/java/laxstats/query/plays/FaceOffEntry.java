package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.FACEOFF)
public class FaceOffEntry extends PlayEntry {

	public FaceOffEntry() {
		playKey = PlayKey.PLAY;
	}
}
