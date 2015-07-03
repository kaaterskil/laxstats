package laxstats.domain.games;

import java.util.Iterator;
import java.util.List;

import laxstats.api.games.GameId;
import laxstats.api.games.GoalUpdated;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlaySequenceNumberChanged;
import laxstats.api.games.PlayTeamScoreChanged;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;
import laxstats.api.games.Strength;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.Period;

/**
 * {@code Goal} represents the domain object model of a goal.
 */
public class Goal extends Play {
   private int sequenceNumber;
   private int teamScore;
   private int opponentScore;
   private Strength strength;
   private int manUpAdvantage;
   private String manUpTeamId;

   /**
    * Creates a {@code Goal} from the given information.
    *
    * @param id
    * @param eventId
    * @param teamId
    * @param period
    * @param elapsedTime
    * @param scoreAttemptType
    * @param comment
    * @param sequenceNumber
    * @param teamScore
    * @param opponentScore
    * @param strength
    * @param manUpAdvantage
    * @param manUpTeamId
    * @param participants
    */
   public Goal(String id, String eventId, String teamId, int period, Period elapsedTime,
      ScoreAttemptType scoreAttemptType, String comment, int sequenceNumber, int teamScore,
      int opponentScore, Strength strength, int manUpAdvantage, String manUpTeamId,
      List<PlayParticipantDTO> participants) {
      super(id, PlayType.GOAL, PlayKey.GOAL, eventId, teamId, period, elapsedTime, scoreAttemptType,
         PlayResult.GOAL, comment, participants);
      this.sequenceNumber = sequenceNumber;
      this.teamScore = teamScore;
      this.opponentScore = opponentScore;
      this.strength = strength;
      this.manUpAdvantage = manUpAdvantage;
      this.manUpTeamId = manUpTeamId;
   }

   /**
    * Creates an empty {@code Goal}. Internal use only.
    */
   protected Goal() {
      super();
   }

   /**
    * Instructs the framework to update this goal with the given play sequence number.
    * 
    * @param sequenceNumber
    */
   public void adjustSequenceNumber(int sequenceNumber) {
      final GameId identifier = new GameId(eventId);
      apply(new PlaySequenceNumberChanged(identifier, id, sequenceNumber));
   }

   /**
    * Instructs the framework to update this goal with the given team score.
    * 
    * @param teamScore
    */
   public void adjustTeamScore(int teamScore) {
      final GameId identifier = new GameId(eventId);
      apply(new PlayTeamScoreChanged(identifier, id, teamScore));
   }

   /**
    * Updates this goal with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(GoalUpdated event) {
      if (!event.getPlayId().equals(id)) {
         return;
      }
      final PlayDTO dto = event.getPlayDTO();
      teamId = dto.getTeam().getId();
      period = dto.getPeriod();
      elapsedTime = dto.getElapsedTime();
      result = dto.getResult();

      sequenceNumber = dto.getSequence();
      teamScore = dto.getTeamScore();
      opponentScore = dto.getOpponentScore();
      strength = dto.getStrength();
      manUpAdvantage = dto.getManUpAdvantage();
      manUpTeamId = dto.getManUpTeam().getId();

      // Update existing scorer and assist
      updateParticipants(dto);
   }

   /**
    * Updates the participants in this goal who match the identifiers in the given information.
    *
    * @param dto
    */
   private void updateParticipants(PlayDTO dto) {
      // FIrst remove any deleted participants
      removeParticipant(dto);

      for (final PlayParticipantDTO pdto : dto.getParticipants()) {
         // Update existing participants
         boolean found = false;
         for (final PlayParticipant p : getParticipants()) {
            if (p.getId().equals(pdto.getId())) {
               found = true;
               p.update(pdto);
            }
         }
         // Create new participant (assist)
         if (!found) {
            final PlayParticipant entity =
               new PlayParticipant(pdto.getId(), id, pdto.getAttendee().getId(), pdto
                  .getTeamSeason().getId(), pdto.getRole(), pdto.isPointCredit(), pdto
                  .getCumulativeAssists(), pdto.getCumulativeGoals());
            participants.add(entity);
         }
      }
   }

   /**
    * Removes the participants from this goal who match the identifiers in the given information.
    *
    * @param dto
    */
   private void removeParticipant(PlayDTO dto) {
      final Iterator<PlayParticipant> iter = getParticipants().iterator();
      while (iter.hasNext()) {
         final PlayParticipant p = iter.next();
         boolean found = false;
         for (final PlayParticipantDTO pdto : dto.getParticipants()) {
            if (p.getId().equals(pdto.getId())) {
               found = true;
            }
         }
         if (!found) {
            iter.remove();
         }
      }
   }

   /**
    * Updates the play sequence number with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PlaySequenceNumberChanged event) {
      if (!event.getPlayId().equals(id)) {
         return;
      }
      sequenceNumber = event.getSequenceNumber();
   }

   /**
    * Updates the team score at the time of this goal with information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PlayTeamScoreChanged event) {
      if (!event.getPlayId().equals(id)) {
         return;
      }
      teamScore = event.getTeamScore();
   }

   /**
    * Returns the team score at the time of this goal.
    *
    * @return
    */
   public int getSequenceNumber() {
      return sequenceNumber;
   }

   /**
    * Sets the team score at the time of this goal.
    *
    * @return
    */
   public int getTeamScore() {
      return teamScore;
   }

   /**
    * Returns the opponent's score at the time of this goal.
    *
    * @return
    */
   public int getOpponentScore() {
      return opponentScore;
   }

   /**
    * Sets the opponent's score at the time of this goal.
    *
    * @param opponentScore
    */
   public void setOpponentScore(int opponentScore) {
      this.opponentScore = opponentScore;
   }

   /**
    * Returns the team strength at the time of this goal.
    *
    * @return
    */
   public Strength getStrength() {
      return strength;
   }

   /**
    * Sets the team strength at the time of this goal.
    *
    * @param strength
    */
   public void setStrength(Strength strength) {
      this.strength = strength;
   }

   /**
    * Returns the man-up advantage at the time of this goal.
    *
    * @return
    */
   public int getManUpAdvantage() {
      return manUpAdvantage;
   }

   /**
    * Sets the man-up advantage at the time of this goal.
    *
    * @param manUpAdvantage
    */
   public void setManUpAdvantage(int manUpAdvantage) {
      this.manUpAdvantage = manUpAdvantage;
   }

   /**
    * Returns the identifier of the man-up team at the time of this goal.
    *
    * @return
    */
   public String getManUpTeamId() {
      return manUpTeamId;
   }

   /**
    * Sets the identifier of the man-up team at the moment of this goal.
    *
    * @param manUpTeamId
    */
   public void setManUpTeamId(String manUpTeamId) {
      this.manUpTeamId = manUpTeamId;
   }
}
