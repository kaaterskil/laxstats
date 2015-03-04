package laxstats.web.events;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

import org.joda.time.Period;

public class FaceOffForm extends AbstractPlayForm {
	private Period elapsedTime;
	private String winnerId;
	private String loserId;

	public FaceOffForm() {
		super(PlayType.FACEOFF, PlayKey.PLAY);
	}

	/*---------- Getter/Setters ----------*/

	public Period getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Period elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	public String getLoserId() {
		return loserId;
	}

	public void setLoserId(String loserId) {
		this.loserId = loserId;
	}
}
