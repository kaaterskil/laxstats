package laxstats.domain.games;

import laxstats.api.games.ClearUpdated;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;

import org.axonframework.eventhandling.annotation.EventHandler;

/**
 * {@code Clear} represents the domain object model of a team clear. A clear is a play designed to
 * move the ball from the defensive end to the offensive end after a save or turnover
 */
public class Clear extends Play {

   /**
    * Creates a {@code Clear} with the given information.
    * 
    * @param id
    * @param eventId
    * @param teamId
    * @param period
    * @param result
    * @param comment
    */
   public Clear(String id, String eventId, String teamId, int period, PlayResult result,
      String comment) {
      super(id, PlayType.CLEAR, PlayKey.PLAY, eventId, teamId, period, null, null, result, comment,
         null);
   }

   /**
    * Creates an empty {@code Clear}. Internal use only.
    */
   protected Clear() {
      super();
   }

   /**
    * Updates and persists changes to ths clear. This event handler is delegated by the aggregate.
    * 
    * @param event
    */
   @EventHandler
   protected void handle(ClearUpdated event) {
      if (!event.getPlayId().equals(id)) {
         return;
      }
      final PlayDTO dto = event.getPlayDTO();
      teamId = dto.getTeam().getId();
      period = dto.getPeriod();
      result = dto.getResult();
      comment = dto.getComment();
   }
}
