package laxstats.domain.games;

import java.util.List;

import laxstats.api.games.GroundBallUpdated;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayType;

import org.axonframework.eventhandling.annotation.EventHandler;

/**
 * {@code GroundBall} represents the domain object model of a ground ball.
 */
public class GroundBall extends Play {

   /**
    * Creates a {@code GroundBall} with the given information.
    * 
    * @param id
    * @param eventId
    * @param teamId
    * @param period
    * @param comment
    * @param participants
    */
   public GroundBall(String id, String eventId, String teamId, int period, String comment,
      List<PlayParticipantDTO> participants) {
      super(id, PlayType.GROUND_BALL, PlayKey.PLAY, eventId, teamId, period, null, null, null,
         comment, participants);
   }

   /**
    * Creates an empty {@code GroundBall}. Internal use only.
    */
   protected GroundBall() {
      super();
   }

   /**
    * Updates and persists this ground ball with information contained in the given event.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(GroundBallUpdated event) {
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
