package laxstats.query.plays;

import laxstats.api.plays.ClearCreatedEvent;
import laxstats.api.plays.FaceOffCreatedEvent;
import laxstats.api.plays.GoalCreatedEvent;
import laxstats.api.plays.GroundBallCreatedEvent;
import laxstats.api.plays.PenaltyCreatedEvent;
import laxstats.api.plays.PlayDTO;
import laxstats.api.plays.ShotCreatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayListener {
	private PlayQueryRepository playRepository;

	@Autowired
	public void setPlayRepository(PlayQueryRepository playRepository) {
		this.playRepository = playRepository;
	}

	@EventHandler
	protected void handle(ClearCreatedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final ClearEntry clear = new ClearEntry();
		clear.setId(event.getPlayId().toString());
		setPropertyValues(clear, dto);
		playRepository.save(clear);
	}

	@EventHandler
	protected void handle(FaceOffCreatedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final FaceOffEntry faceoff = new FaceOffEntry();
		faceoff.setId(event.getPlayId().toString());
		setPropertyValues(faceoff, dto);
		playRepository.save(faceoff);
	}

	@EventHandler
	protected void handle(GoalCreatedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final GoalEntry goal = new GoalEntry();
		goal.setId(event.getPlayId().toString());
		setPropertyValues(goal, dto);
		playRepository.save(goal);
	}

	@EventHandler
	protected void handle(GroundBallCreatedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final GroundBallEntry groundBall = new GroundBallEntry();
		groundBall.setId(event.getPlayId().toString());
		setPropertyValues(groundBall, dto);
		playRepository.save(groundBall);
	}

	@EventHandler
	protected void handle(PenaltyCreatedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final PenaltyEntry penalty = new PenaltyEntry();
		penalty.setId(event.getPlayId().toString());
		setPropertyValues(penalty, dto);
		playRepository.save(penalty);
	}

	@EventHandler
	protected void handle(ShotCreatedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final ShotEntry shot = new ShotEntry();
		shot.setId(event.getPlayId().toString());
		setPropertyValues(shot, dto);
		playRepository.save(shot);
	}

	private void setPropertyValues(PlayEntry obj, PlayDTO dto) {
		obj.setEvent(dto.getEvent());
		obj.setSequenceNumber(dto.getSequence());
		obj.setTeam(dto.getTeam());
		obj.setPeriod(dto.getPeriod());
		obj.setElapsedTime(dto.getElapsedTime());
		obj.setScoreAttemptType(dto.getAttemptType());
		obj.setResult(dto.getResult());
		obj.setTeamScore(dto.getTeamScore());
		obj.setOpponentScore(dto.getOpponentScore());
		obj.setStrength(dto.getStrength());
		obj.setManUpAdvantage(dto.getManUpAdvantage());
		obj.setManUpTeam(dto.getManUpTeam());
		obj.setComment(dto.getComment());
		obj.setCreatedAt(dto.getCreatedAt());
		obj.setCreatedBy(dto.getCreatedBy());
		obj.setModifiedAt(dto.getModifiedAt());
		obj.setModifiedBy(dto.getModifiedBy());
	}
}
