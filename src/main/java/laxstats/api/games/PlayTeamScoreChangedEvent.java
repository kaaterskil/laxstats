package laxstats.api.games;

public class PlayTeamScoreChangedEvent {
	private final GameId gameId;
	private final String playId;
	private final int teamScore;

	public PlayTeamScoreChangedEvent(GameId gameId, String playId,
			int teamScore) {
		this.gameId = gameId;
		this.playId = playId;
		this.teamScore = teamScore;
	}

	public GameId getEventId() {
		return gameId;
	}

	public String getPlayId() {
		return playId;
	}

	public int getTeamScore() {
		return teamScore;
	}
}
