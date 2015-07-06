package laxstats.web.games;

import laxstats.api.games.AthleteStatus;
import laxstats.api.players.Role;

public interface AttendeeResource {

   /**
    * Returns the attendee's unique identifier, or null if the attendee has not been persisted.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the attendee's unique identifier. Use null if the attendee has not been persisted.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the identifier of the associated player. Never null.
    *
    * @return
    */
   public String getPlayerId();

   /**
    * Sets the identifier of the associated player. Must not be null.
    *
    * @param playerId
    */
   public void setPlayerId(String playerId);

   /**
    * Returns the identifier of the attendee's associated game. Never null.
    *
    * @return
    */
   public String getGameId();

   /**
    * Sets the identifier of the attendee's associated game. Must not be null.
    *
    * @param gameId
    */
   public void setGameId(String gameId);

   /**
    * Returns the game attendee's role. Never null.
    *
    * @return
    */
   public Role getRole();

   /**
    * Sets the game attendee's role. Must not be null.
    *
    * @param role
    */
   public void setRole(Role role);

   /**
    * Returns the game attendee's playing status, or null if not an athlete.
    *
    * @return
    */
   public AthleteStatus getStatus();

   /**
    * Sets the game attendee's playing status. Use null if not an athlete.
    *
    * @param status
    */
   public void setStatus(AthleteStatus status);

}
