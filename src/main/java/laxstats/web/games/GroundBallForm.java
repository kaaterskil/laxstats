package laxstats.web.games;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

public class GroundBallForm extends AbstractPlayForm {
	private String playerId;

	public GroundBallForm() {
		super(PlayType.GROUND_BALL, PlayKey.PLAY);
	}

	/*---------- Getter/Setters ----------*/

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
}
