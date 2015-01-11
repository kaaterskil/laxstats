package laxstats.web.events;

public class FaceOffForm extends AbstractPlayForm {
	private String winnerId;
	private String loserId;

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
