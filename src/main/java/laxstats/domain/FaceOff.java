package laxstats.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FACEOFF")
public class FaceOff extends Play {
	
	public FaceOff() {
		playKey = PlayKey.PLAY;
	}
}
