package laxstats.query.teamSeasons;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

/**
 * Thrown when a team season resource cannot be found for the given date.
 */
public class TeamSeasonNotFoundByDateException extends ResourceNotFoundException {

   private static final long serialVersionUID = 4369838653717731606L;

   /**
    * @param message
    */
   public TeamSeasonNotFoundByDateException(String dateString) {
      super("No team season found that includes date: " + dateString);
   }

}
