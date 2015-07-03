package laxstats.domain.games;

import java.util.List;

import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;
import laxstats.api.games.ShotUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.Period;

/**
 * {@code Shot} represents the domain object model of a score attempt.
 */
public class Shot extends Play {

   /**
    * Creates a {@code Shot} with the given information.
    *
    * @param id
    * @param eventId
    * @param teamId
    * @param period
    * @param elapsedTime
    * @param scoreAttemptType
    * @param result
    * @param comment
    * @param participants
    */
   public Shot(String id, String eventId, String teamId, int period, Period elapsedTime,
      ScoreAttemptType scoreAttemptType, PlayResult result, String comment,
      List<PlayParticipantDTO> participants) {
      super(id, PlayType.SHOT, PlayKey.PLAY, eventId, teamId, period, elapsedTime, scoreAttemptType,
         result, comment, participants);
   }

   /**
    * Creates an empty ({@code Shot}. Internal use only.
    */
   protected Shot() {
      super();
   }

   /**
    * Updates and persists changes to an existing shot with information contained in the given
    * event. This event handler is delegated by the game aggregate.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ShotUpdated event) {
      if (!event.getPlayId().equals(id)) {
         return;
      }

      final PlayDTO dto = event.getPlayDTO();
      teamId = dto.getTeam().getId();
      period = dto.getPeriod();
      elapsedTime = dto.getElapsedTime();
      comment = dto.getComment();

      scoreAttemptType = dto.getAttemptType();
      result = dto.getResult();
      for (final PlayParticipant participant : participants) {
         updateParticipant(participant, dto.getParticipants());
      }
   }
}
