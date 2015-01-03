package laxstats.query.events;

import laxstats.api.events.ClearRecordedEvent;
import laxstats.api.events.FaceOffRecordedEvent;
import laxstats.api.events.GoalRecordedEvent;
import laxstats.api.events.GroundBallRecordedEvent;
import laxstats.api.events.PenaltyCreatedEvent;
import laxstats.api.events.PlayDTO;
import laxstats.api.events.ShotRecordedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayListener {
	private PlayQueryRepository repository;

	@Autowired
	public void setPlayRepository(PlayQueryRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	protected void handle(ClearRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final ClearEntry clear = new ClearEntry();
		clear.setId(event.getPlayId());
		setPropertyValues(clear, dto);
		repository.save(clear);
	}

	@EventHandler
	protected void handle(FaceOffRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final FaceOffEntry faceoff = new FaceOffEntry();
		faceoff.setId(event.getPlayId());
		setPropertyValues(faceoff, dto);
		repository.save(faceoff);
	}

	@EventHandler
	protected void handle(GoalRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final GoalEntry goal = new GoalEntry();
		goal.setId(event.getPlayId());
		setPropertyValues(goal, dto);
		repository.save(goal);
	}

	@EventHandler
	protected void handle(GroundBallRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final GroundBallEntry groundBall = new GroundBallEntry();
		groundBall.setId(event.getPlayId());
		setPropertyValues(groundBall, dto);
		repository.save(groundBall);
	}

	@EventHandler
	protected void handle(PenaltyCreatedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final PenaltyEntry penalty = new PenaltyEntry();
		penalty.setId(event.getPlayId());
		setPropertyValues(penalty, dto);
		repository.save(penalty);
	}

	@EventHandler
	protected void handle(ShotRecordedEvent event) {
		final PlayDTO dto = event.getPlayDTO();

		final ShotEntry shot = new ShotEntry();
		shot.setId(event.getPlayId());
		setPropertyValues(shot, dto);
		repository.save(shot);
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
