package laxstats.web.events;

public class FaceOffForm extends AbstractPlayForm {
	private String winningTeamId;
	private String winnerId;
	private String losingTeamId;
	private String loserId;

	public String getWinningTeamId() {
		return winningTeamId;
	}

	public void setWinningTeamId(String winningTeamId) {
		this.winningTeamId = winningTeamId;
	}

	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	public String getLosingTeamId() {
		return losingTeamId;
	}

	public void setLosingTeamId(String losingTeamId) {
		this.losingTeamId = losingTeamId;
	}

	public String getLoserId() {
		return loserId;
	}

	public void setLoserId(String loserId) {
		this.loserId = loserId;
	}
}
