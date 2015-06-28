package laxstats.query.games;

import java.util.Iterator;

import laxstats.api.games.ClearDeletedEvent;
import laxstats.api.games.ClearRecordedEvent;
import laxstats.api.games.ClearUpdatedEvent;
import laxstats.api.games.FaceOffDeletedEvent;
import laxstats.api.games.FaceOffRecordedEvent;
import laxstats.api.games.FaceOffUpdatedEvent;
import laxstats.api.games.GoalDeletedEvent;
import laxstats.api.games.GoalRecordedEvent;
import laxstats.api.games.GoalUpdatedEvent;
import laxstats.api.games.GroundBallDeletedEvent;
import laxstats.api.games.GroundBallRecordedEvent;
import laxstats.api.games.GroundBallUpdatedEvent;
import laxstats.api.games.PenaltyDeletedEvent;
import laxstats.api.games.PenaltyRecordedEvent;
import laxstats.api.games.PenaltyUpdatedEvent;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.ShotDeletedEvent;
import laxstats.api.games.ShotRecordedEvent;
import laxstats.api.games.ShotUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayListener {
	private GameQueryRepository repository;

	@Autowired
	public void setPlayRepository(GameQueryRepository repository) {
		this.repository = repository;
	}

	/*---------- Clear event handlers ----------*/

	@EventHandler
	protected void handle(ClearRecordedEvent event) {
		final ClearEntry clear = new ClearEntry();
		setPropertyValues(clear, event.getPlayDTO());

		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		aggregate.addPlay(clear);
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(ClearUpdatedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		updatePropertyValues(play, event.getPlayDTO());
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(ClearDeletedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		aggregate.deletePlay(play);
		repository.save(aggregate);
	}

	/*---------- FaceOff event handlers ----------*/

	@EventHandler
	protected void handle(FaceOffRecordedEvent event) {
		final FaceOffEntry faceoff = new FaceOffEntry();
		setPropertyValues(faceoff, event.getPlayDTO());

		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		aggregate.addPlay(faceoff);
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(FaceOffUpdatedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		updatePropertyValues(play, event.getPlayDTO());
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(FaceOffDeletedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		aggregate.deletePlay(play);
		repository.save(aggregate);
	}

	/*---------- Goal event handlers ----------*/

	@EventHandler
	protected void handle(GoalRecordedEvent event) {
		final GoalEntry goal = new GoalEntry();
		setPropertyValues(goal, event.getPlayDTO());

		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		aggregate.addPlay(goal);
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(GoalUpdatedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		updatePropertyValues(play, event.getPlayDTO());
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(GoalDeletedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		aggregate.deletePlay(play);
		repository.save(aggregate);
	}

	/*---------- Ground Ball event handlers ----------*/

	@EventHandler
	protected void handle(GroundBallRecordedEvent event) {
		final GroundBallEntry groundBall = new GroundBallEntry();
		setPropertyValues(groundBall, event.getPlayDTO());

		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		aggregate.addPlay(groundBall);
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(GroundBallUpdatedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		updatePropertyValues(play, event.getPlayDTO());
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(GroundBallDeletedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		aggregate.deletePlay(play);
		repository.save(aggregate);
	}

	/*---------- Penalty event handlers ----------*/

	@EventHandler
	protected void handle(PenaltyRecordedEvent event) {
		final PenaltyEntry penalty = new PenaltyEntry();
		setPropertyValues(penalty, event.getPlayDTO());

		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		aggregate.addPlay(penalty);
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(PenaltyUpdatedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		updatePropertyValues(play, event.getPlayDTO());
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(PenaltyDeletedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		aggregate.deletePlay(play);
		repository.save(aggregate);
	}

	/*---------- Shot event handlers ----------*/

	@EventHandler
	protected void handle(ShotRecordedEvent event) {
		final ShotEntry shot = new ShotEntry();
		setPropertyValues(shot, event.getPlayDTO());

		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		aggregate.addPlay(shot);
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(ShotUpdatedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		updatePropertyValues(play, event.getPlayDTO());
		repository.save(aggregate);
	}

	@EventHandler
	protected void handle(ShotDeletedEvent event) {
		final String eventId = event.getEventId().toString();
		final GameEntry aggregate = repository.findOne(eventId);
		final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
		aggregate.deletePlay(play);
		repository.save(aggregate);
	}

	/*---------- Utility methods ----------*/

	private void setPropertyValues(PlayEntry obj, PlayDTO dto) {
		obj.setId(dto.getIdentifier());
		obj.setTeam(dto.getTeam());
		obj.setPeriod(dto.getPeriod());
		obj.setElapsedTime(dto.getElapsedTime());
		obj.setScoreAttemptType(dto.getAttemptType());
		obj.setResult(dto.getResult());
		obj.setComment(dto.getComment());

		// Set goal values
		obj.setSequenceNumber(dto.getSequence());
		obj.setTeamScore(dto.getTeamScore());
		obj.setOpponentScore(dto.getOpponentScore());
		obj.setStrength(dto.getStrength());
		obj.setManUpAdvantage(dto.getManUpAdvantage());
		obj.setManUpTeam(dto.getManUpTeam());

		// Set penalty values
		obj.setViolation(dto.getViolation());
		obj.setDuration(dto.getPenaltyDuration());

		// Set audit values
		obj.setCreatedAt(dto.getCreatedAt());
		obj.setCreatedBy(dto.getCreatedBy());
		obj.setModifiedAt(dto.getModifiedAt());
		obj.setModifiedBy(dto.getModifiedBy());

		// Create play participants
		setPlayParticipants(obj, dto);
	}

	private void setPlayParticipants(PlayEntry obj, PlayDTO dto) {
		for (final PlayParticipantDTO pdto : dto.getParticipants()) {
			setPlayParticipant(obj, pdto);
		}
	}

	private void setPlayParticipant(PlayEntry obj, PlayParticipantDTO pdto) {
		final PlayParticipantEntry p = new PlayParticipantEntry();
		p.setId(pdto.getId());
		p.setPlay(obj);
		p.setAttendee(pdto.getAttendee());
		p.setTeamSeason(pdto.getTeamSeason());
		p.setRole(pdto.getRole());
		p.setPointCredit(pdto.isPointCredit());
		p.setCumulativeAssists(pdto.getCumulativeAssists());
		p.setCumulativeGoals(pdto.getCumulativeGoals());
		p.setCreatedAt(pdto.getCreatedAt());
		p.setCreatedBy(pdto.getCreatedBy());
		p.setModifiedAt(pdto.getModifiedAt());
		p.setModifiedBy(pdto.getModifiedBy());
		obj.addParticipant(p);
	}

	private void updatePropertyValues(PlayEntry obj, PlayDTO dto) {
		obj.setId(dto.getIdentifier());
		obj.setTeam(dto.getTeam());
		obj.setPeriod(dto.getPeriod());
		obj.setElapsedTime(dto.getElapsedTime());
		obj.setScoreAttemptType(dto.getAttemptType());
		obj.setResult(dto.getResult());
		obj.setComment(dto.getComment());

		// Update goal values
		obj.setSequenceNumber(dto.getSequence());
		obj.setTeamScore(dto.getTeamScore());
		obj.setOpponentScore(dto.getOpponentScore());
		obj.setStrength(dto.getStrength());
		obj.setManUpAdvantage(dto.getManUpAdvantage());
		obj.setManUpTeam(dto.getManUpTeam());

		// Update penalty values
		obj.setViolation(dto.getViolation());
		obj.setDuration(dto.getPenaltyDuration());

		// Update audit values
		obj.setModifiedAt(dto.getModifiedAt());
		obj.setModifiedBy(dto.getModifiedBy());

		// Update play participants
		updatePlayParticipants(obj, dto);
	}

	private void updatePlayParticipants(PlayEntry obj, PlayDTO dto) {
		// First remove any deleted participants
		removePlayParticipant(obj, dto);

		for (final PlayParticipantDTO pdto : dto.getParticipants()) {
			// Update existing participant
			boolean found = false;
			for (final PlayParticipantEntry p : obj.getParticipants()) {
				if (p.getId().equals(pdto.getId())) {
					found = true;
					updatePlayParticipant(p, pdto);
				}
			}
			// Create new participant
			if (!found) {
				setPlayParticipant(obj, pdto);
			}
		}
	}

	private void removePlayParticipant(PlayEntry obj, PlayDTO dto) {
		final Iterator<PlayParticipantEntry> iter = obj.getParticipants()
				.iterator();
		while (iter.hasNext()) {
			final PlayParticipantEntry p = iter.next();
			boolean found = false;
			for (final PlayParticipantDTO pdto : dto.getParticipants()) {
				if (p.getId().equals(pdto.getId())) {
					found = true;
				}
			}
			if (!found) {
				p.clear();
				iter.remove();
			}
		}
	}

	private void updatePlayParticipant(PlayParticipantEntry p,
			PlayParticipantDTO pdto) {
		p.setAttendee(pdto.getAttendee());
		p.setTeamSeason(pdto.getTeamSeason());
		p.setRole(pdto.getRole());
		p.setPointCredit(pdto.isPointCredit());
		p.setCumulativeAssists(pdto.getCumulativeAssists());
		p.setCumulativeGoals(pdto.getCumulativeGoals());
		p.setModifiedAt(pdto.getModifiedAt());
		p.setModifiedBy(pdto.getModifiedBy());
	}
}