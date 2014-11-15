package laxstats.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GROUND_BALL")
public class GroundBall extends Play {
	
	public GroundBall() {
		playKey = PlayKey.PLAY;
	}
}
