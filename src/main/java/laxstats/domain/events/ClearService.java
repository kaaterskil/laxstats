package laxstats.domain.events;

import laxstats.api.events.PlayDTO;

public class ClearService extends PlayServiceImpl {

	public ClearService(Event event) {
		super(event);
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
