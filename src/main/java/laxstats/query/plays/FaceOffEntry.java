package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.plays.PlayKey;

@Entity
@DiscriminatorValue("FACEOFF")
public class FaceOffEntry extends PlayEntry {

	public FaceOffEntry() {
		playKey = PlayKey.PLAY;
	}
}
