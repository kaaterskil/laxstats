package laxstats.query.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SHOT")
public class Shot extends Play {
	
	public Shot() {
		playKey = PlayKey.PLAY;
	}
}
