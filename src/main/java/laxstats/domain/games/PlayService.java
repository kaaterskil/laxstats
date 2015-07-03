package laxstats.domain.games;

import java.util.List;

import laxstats.api.games.PlayDTO;

/**
 * {@code PlayService} defines an interface for common domain functions.
 */
public interface PlayService {
   /**
    * Returns the game aggregate for this service.
    *
    * @return the game aggregate for this service.
    */
   Game getEvent();

   /**
    * Returns a list of plays registered with this game aggregate. If the specified discriminator is
    * not null, only plays of that discriminator type will be returned. If the plays record elapsed
    * time, the list is ordered by the total elapsed time since the beginning of the Game.
    *
    * @param discriminator String value representing the play type. May be null.
    * @return A list of the event aggregate's plays.
    */
   List<Play> getPlays(String discriminator);

   /**
    * Returns true if the play represented by the specified DTO can be recorded with the game
    * aggregate, false otherwise.
    *
    * @param dto the data transfer object representing the specified play.
    * @return True if the play represented by the specified DTO can be recorded with the game
    *         aggregate, false otherwise.
    */
   boolean canRecordPlay(PlayDTO dto);

   /**
    * Returns true if the play represented by the specified DTO can be updated, false otherwise.
    *
    * @param dto the data transfer object representing the specified play.
    * @return True if the play represented by the specified DTO can be updated, false otherwise.
    */
   boolean canUpdatePlay(PlayDTO dto);

   /**
    * Tests that the play represented by the specified DTO is not already registered with the game
    * aggregate.
    *
    * @param dto the data transfer object representing the specified play.
    * @return True if the specified play is already registered with the game aggregate, false
    *         otherwise.
    */
   boolean playRecorded(PlayDTO dto);

   /**
    * Tests that the participants in the play represented by the specified DTO are registered with
    * the game aggregate as attendees. Returns true if the participants are also attendees, false
    * otherwise.
    *
    * @param dto the data transfer object representing the specified play.
    * @return True if the participants int he specified play are registered as attendees with this
    *         <code>Game</code> aggregate, false otherwise.
    */
   boolean participantsRegistered(PlayDTO dto);

   /**
    * Computes and sets invariant values on the DTO representing the specified play.
    *
    * @param dto the data transfer object representing the specified play.
    */
   void setInvariants(PlayDTO dto);
}
