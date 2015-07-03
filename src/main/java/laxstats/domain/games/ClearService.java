package laxstats.domain.games;

import laxstats.api.games.PlayDTO;

/**
 * {@code ClearService} provides specialized implementation of common domain functions.
 */
public class ClearService extends PlayServiceImpl {

   /**
    * Creates a {@code ClearService} with the given game.
    *
    * @param game
    */
   public ClearService(Game game) {
      super(game);
   }

   /**
    * Returns true if the play matching the identifier contained in the given information does not
    * exist, false otherwise.
    */
   @Override
   public boolean canRecordPlay(PlayDTO dto) {
      if (playRecorded(dto)) {
         return false;
      }
      return true;
   }

   /**
    * Returns true if the play matching the identifier in the given information exists, false
    * otherwise.
    */
   @Override
   public boolean canUpdatePlay(PlayDTO dto) {
      if (!playRecorded(dto)) {
         return false;
      }
      return true;
   }
}
