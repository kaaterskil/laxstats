package laxstats.domain.games;

import laxstats.api.games.PlayDTO;

public class ClearService extends PlayServiceImpl {

	public ClearService(Game game) {
		super(game);
	}

	@Override
	public boolean canRecordPlay(PlayDTO dto) {
		if (playRecorded(dto)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean canUpdatePlay(PlayDTO dto) {
		if (!playRecorded(dto)) {
			return false;
		}
		return true;
	}
}
