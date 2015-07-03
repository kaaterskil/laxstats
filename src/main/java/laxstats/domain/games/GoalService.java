package laxstats.domain.games;

import java.util.List;

import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayType;

import org.joda.time.Period;

/**
 * {@code GoalService} provides specialized implementation for common domain functions.
 */
public class GoalService extends PlayServiceImpl {

   /**
    * Creates a {@code GoalService} with the given game.
    *
    * @param game
    */
   public GoalService(Game game) {
      super(game);
   }

   /**
    * Returns true if the play represented by the given information already exists, false otherwise.
    * Tests include whether the identifier of the given goal matches an identifier already
    * registered with the game aggregate, and whether a goal with a different identifier but made by
    * the same team at the same time exists and is registered.
    */
   @Override
   public boolean playRecorded(PlayDTO dto) {
      final String key = dto.getIdentifier().toString();
      if (getEvent().getPlays().containsKey(key)) {
         return true;
      }
      for (final Play each : getPlays(null)) {
         if (each.getDiscriminator().equals(dto.getDiscriminator()) &&
            each.getPlayKey().equals(dto.getPlayKey()) &&
            each.getTeamId().equals(dto.getTeam().getId()) && each.getPeriod() == dto.getPeriod() &&
            each.getElapsedTime().equals(dto.getElapsedTime())) {
            return true;
         }
      }
      return false;
   }

   /**
    * Sets the computed invariants for this play. Invariants include the play sequence number, the
    * team score, and the opponent score.
    *
    * If this play is new and is inserted into an existing collection of plays (in other words,
    * created out of order), the invariants of the existing plays are re-computed and persisted.
    */
   @Override
   public void setInvariants(PlayDTO dto) {
      final List<Play> goals = getPlays(PlayType.GOAL);
      final Period elapsedTime = dto.getTotalElapsedTime();
      final String teamId = dto.getTeam().getId();
      boolean prepended = false;
      boolean sequenceSet = false;
      boolean scoreSet = false;

      int sequence = 0;
      int teamScore = 0;
      int opponentScore = 0;
      for (final Play each : goals) {
         final Goal goal = (Goal)each;

         // For 1-based values
         if (goal.getTeamId().equals(teamId)) {
            teamScore++;
         }
         else {
            opponentScore++;
         }

         // For goals inserted into an existing sequence
         if (goal.getTotalElapsedTime().toStandardSeconds().getSeconds() > elapsedTime
            .toStandardSeconds().getSeconds()) {
            prepended = true;

            // Set sequence number
            if (!sequenceSet) {
               dto.setSequence(sequence++);
               sequenceSet = true;
            }
            goal.adjustSequenceNumber(sequence);

            // Set team score
            if (goal.getTeamId().equals(teamId)) {
               if (!scoreSet) {
                  dto.setTeamScore(teamScore++);
                  dto.setOpponentScore(opponentScore);
                  scoreSet = true;
               }
               goal.adjustTeamScore(teamScore);
            }
         }

         // For 0-based values
         sequence++;
      }
      // For goals appended to an existing sequence
      if (!prepended) {
         dto.setSequence(sequence);
         dto.setTeamScore(teamScore + 1);
         dto.setOpponentScore(opponentScore);
      }
   }
}
