package laxstats.domain.games;

import java.util.List;

import laxstats.api.games.FaceOffUpdated;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayType;

import org.axonframework.eventhandling.annotation.EventHandler;

/**
 * {@code FaceOff} represents the domain object model of a face-off. A face-off takes place at the
 * start of each quarter, after every goal, and after certain dead balls. Two opposing players
 * crouch down at midfield, hold their sticks flat on the ground and press the backs of their stick
 * pockets together. The ball is then placed between the pockets and, when signaled to start, the
 * players “rake” or clamp on the ball to vie for control.
 */
public class FaceOff extends Play {

   /**
    * Creates a {@code FaceOff} with the given information.
    *
    * @param id
    * @param eventId
    * @param teamId
    * @param period
    * @param comment
    * @param participants
    */
   public FaceOff(String id, String eventId, String teamId, int period, String comment,
      List<PlayParticipantDTO> participants) {
      super(id, PlayType.FACEOFF, PlayKey.PLAY, eventId, teamId, period, null, null, null, comment,
         participants);
   }

   /**
    * Creates an empty {@code FaceOff}. Internal use only.
    */
   protected FaceOff() {
      super();
   }

   /**
    * Updates and persists changes to an existing face-off with information contained in the given
    * event. This event handler is delegated from the aggregate.
    *
    * @param event
    */
   @EventHandler
   protected void handle(FaceOffUpdated event) {
      if (!event.getPlayId().equals(id)) {
         return;
      }
      final PlayDTO dto = event.getPlayDTO();
      teamId = dto.getTeam().getId();
      period = dto.getPeriod();
      comment = dto.getComment();
      for (final PlayParticipant participant : participants) {
         updateParticipant(participant, dto.getParticipants());
      }
   }
}
