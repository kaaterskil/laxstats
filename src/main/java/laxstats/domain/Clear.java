package laxstats.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLEAR")
public class Clear extends Play {
	
	public Clear() {
		playKey = PlayKey.PLAY;
	}
}
