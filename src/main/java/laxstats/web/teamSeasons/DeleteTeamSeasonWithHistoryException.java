package laxstats.web.teamSeasons;

/**
 * Thrown when a team season is being deleted and has created or modified games or plays.
 */
public class DeleteTeamSeasonWithHistoryException extends IllegalArgumentException {

   private static final long serialVersionUID = -8381122071035410216L;

   /**
    * Creates a {@code DeleteTeamSeasonWithHistoryException}.
    */
   public DeleteTeamSeasonWithHistoryException() {
      super("Cannot delete team season with game or play history");
   }

}
