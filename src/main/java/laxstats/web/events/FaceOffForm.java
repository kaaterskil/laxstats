package laxstats.web.events;

import org.joda.time.LocalTime;

public class FaceOffForm extends AbstractPlayForm {
	private LocalTime elapsedTime;
	private String winnerId;
	private String loserId;

	public LocalTime getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(LocalTime elapsedTime) {
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
