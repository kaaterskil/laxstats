package laxstats.query.plays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayListener {

	private PlayQueryRepository playRepository;

	@Autowired
	public void setPlayRepository(PlayQueryRepository playRepository) {
		this.playRepository = playRepository;
	}
}
