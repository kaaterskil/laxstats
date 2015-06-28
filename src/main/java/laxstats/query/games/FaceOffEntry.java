package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

@Entity
@DiscriminatorValue(PlayType.FACEOFF)
public class FaceOffEntry extends PlayEntry {
	private static final long serialVersionUID = 2039846177366422767L;

	public FaceOffEntry() {
		playKey = PlayKey.PLAY;
	}
}
