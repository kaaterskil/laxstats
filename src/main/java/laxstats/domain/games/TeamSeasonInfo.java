package laxstats.domain.games;

import laxstats.api.games.Alignment;

/**
 * {@code TeamSeasonInfo} is a value object representing the team season aggregate. Updates
 * representing changes in state for the team season simply replace this object with a new one with
 * the same identifier.
 */
public class TeamSeasonInfo {
   private final String teamSeasonId;
   private final String name;
   private final Alignment alignment;

   /**
    * Creates a {@code TeamSeasonInfo} with the given information.
    *
    * @param teamSeasonId
    * @param name
    * @param alignment
    */
   public TeamSeasonInfo(String teamSeasonId, String name, Alignment alignment) {
      this.teamSeasonId = teamSeasonId;
      this.name = name;
      this.alignment = alignment;
   }

   /**
    * Returns the team season identifier.
    *
    * @return
    */
   public String getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Returns the team name.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the team alignment.
    *
    * @return
    */
   public Alignment getAlignment() {
      return alignment;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return teamSeasonId.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (obj != null && obj instanceof TeamSeasonInfo) {
         final TeamSeasonInfo that = (TeamSeasonInfo)obj;
         return teamSeasonId.equals(that.teamSeasonId);
      }
      return false;
   }
}
