package laxstats.query.games;

import java.util.Iterator;

import laxstats.api.games.ClearDeleted;
import laxstats.api.games.ClearRecorded;
import laxstats.api.games.ClearUpdated;
import laxstats.api.games.FaceOffDeleted;
import laxstats.api.games.FaceOffRecorded;
import laxstats.api.games.FaceOffUpdated;
import laxstats.api.games.GoalDeleted;
import laxstats.api.games.GoalRecorded;
import laxstats.api.games.GoalUpdated;
import laxstats.api.games.GroundBallDeleted;
import laxstats.api.games.GroundBallRecorded;
import laxstats.api.games.GroundBallUpdated;
import laxstats.api.games.PenaltyDeleted;
import laxstats.api.games.PenaltyRecorded;
import laxstats.api.games.PenaltyUpdated;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.ShotDeleted;
import laxstats.api.games.ShotRecorded;
import laxstats.api.games.ShotUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code PlayListener} managed events that write to the query database.
 */
@Component
public class PlayListener {
   private GameQueryRepository repository;

   @Autowired
   public void setPlayRepository(GameQueryRepository repository) {
      this.repository = repository;
   }

   /**
    * Creates and persists a clear from informaion contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(ClearRecorded event) {
      final ClearEntry clear = new ClearEntry();
      setPropertyValues(clear, event.getPlayDTO());

      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      aggregate.addPlay(clear);
      repository.save(aggregate);
   }

   /**
    * Updaates and persists an exiting clear with information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(ClearUpdated event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      updatePropertyValues(play, event.getPlayDTO());
      repository.save(aggregate);
   }

   /**
    * Deletes the clear matching the identifier contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(ClearDeleted event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      aggregate.deletePlay(play);
      repository.save(aggregate);
   }

   /**
    * Creates and persists a face-off from information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(FaceOffRecorded event) {
      final FaceOffEntry faceoff = new FaceOffEntry();
      setPropertyValues(faceoff, event.getPlayDTO());

      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      aggregate.addPlay(faceoff);
      repository.save(aggregate);
   }

   /**
    * Updates and persists an existing face-off with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(FaceOffUpdated event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      updatePropertyValues(play, event.getPlayDTO());
      repository.save(aggregate);
   }

   /**
    * Deletes the face-off matching the identifier contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(FaceOffDeleted event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      aggregate.deletePlay(play);
      repository.save(aggregate);
   }

   /**
    * Creates and persists a goal from information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GoalRecorded event) {
      final GoalEntry goal = new GoalEntry();
      setPropertyValues(goal, event.getPlayDTO());

      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      aggregate.addPlay(goal);
      repository.save(aggregate);
   }

   /**
    * Updates and persists an existing goal with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GoalUpdated event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      updatePropertyValues(play, event.getPlayDTO());
      repository.save(aggregate);
   }

   /**
    * Deletes the goal matching the identifier contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GoalDeleted event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      aggregate.deletePlay(play);
      repository.save(aggregate);
   }

   /**
    * Creates and persists a ground ball from information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GroundBallRecorded event) {
      final GroundBallEntry groundBall = new GroundBallEntry();
      setPropertyValues(groundBall, event.getPlayDTO());

      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      aggregate.addPlay(groundBall);
      repository.save(aggregate);
   }

   /**
    * Updates and persists en existing ground ball with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GroundBallUpdated event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      updatePropertyValues(play, event.getPlayDTO());
      repository.save(aggregate);
   }

   /**
    * Deletes the ground ball matching the identifier contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GroundBallDeleted event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      aggregate.deletePlay(play);
      repository.save(aggregate);
   }

   /**
    * Creates and persists a penalty from information contained in thie given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PenaltyRecorded event) {
      final PenaltyEntry penalty = new PenaltyEntry();
      setPropertyValues(penalty, event.getPlayDTO());

      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      aggregate.addPlay(penalty);
      repository.save(aggregate);
   }

   /**
    * Updates and persists an existing penalty with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PenaltyUpdated event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      updatePropertyValues(play, event.getPlayDTO());
      repository.save(aggregate);
   }

   /**
    * Deletes the penalty matching the identifier contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PenaltyDeleted event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      aggregate.deletePlay(play);
      repository.save(aggregate);
   }

   /**
    * Creates and persists a shot from information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ShotRecorded event) {
      final ShotEntry shot = new ShotEntry();
      setPropertyValues(shot, event.getPlayDTO());

      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      aggregate.addPlay(shot);
      repository.save(aggregate);
   }

   /**
    * Updates and persits an existing shot with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ShotUpdated event) {
      final String eventId = event.getEventId().toString();
      final GameEntry aggregate = repository.findOne(eventId);
      final PlayEntry play = aggregate.getPlays().get(event.getPlayId());
      updatePropertyValues(play, event.getPlayDTO());
      repository.save(aggregate);
   }

   /**
    * Deletes the shot that matches the identifier contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ShotDeleted event) {
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
      final Iterator<PlayParticipantEntry> iter = obj.getParticipants().iterator();
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

   private void updatePlayParticipant(PlayParticipantEntry p, PlayParticipantDTO pdto) {
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
