package laxstats.query.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.FACEOFF)
public class FaceOffEntry extends PlayEntry {
	private static final long serialVersionUID = 2039846177366422767L;

	public FaceOffEntry() {
		playKey = PlayKey.PLAY;
	}
}
