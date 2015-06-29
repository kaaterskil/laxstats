package laxstats.domain.teams;

import java.io.Serializable;

import laxstats.api.teamSeasons.TeamStatus;

import org.joda.time.LocalDate;

/**
 * {@code TeamSeasonInfo} is a value object representing a season within a team.
 */
public class TeamSeasonInfo implements Serializable {
   private static final long serialVersionUID = -2527201731480782673L;

   private final String id;
   private final String teamId;
   private final String seasonId;
   private final LocalDate startsOn;
   private final LocalDate endsOn;
   private final TeamStatus status;

   /**
    * Creates a {@code TeamSeasonInfo} with the given information.
    *
    * @param id
    * @param teamId
    * @param seasonId
    * @param startsOn
    * @param endsOn
    * @param status
    */
   public TeamSeasonInfo(String id, String teamId, String seasonId, LocalDate startsOn,
      LocalDate endsOn, TeamStatus status) {
      this.id = id;
      this.teamId = teamId;
      this.seasonId = seasonId;
      this.startsOn = startsOn;
      this.endsOn = endsOn;
      this.status = status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return id.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (obj != null && obj instanceof TeamSeasonInfo) {
         final TeamSeasonInfo that = (TeamSeasonInfo)obj;
         return id.equals(that.id);
      }
      return false;
   }

   /**
    * Returns the unique identifier of this team season.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the {@code Team} identifier.
    *
    * @return
    */
   public String getTeamId() {
      return teamId;
   }

   /**
    * Returns the {@code Season} identifier.
    *
    * @return
    */
   public String getSeasonId() {
      return seasonId;
   }

   /**
    * Returns the season start date.
    *
    * @return
    */
   public LocalDate getStartsOn() {
      return startsOn;
   }

   /**
    * Returns the season end date.
    *
    * @return
    */
   public LocalDate getEndsOn() {
      return endsOn;
   }

   /**
    * Returns the team status.
    *
    * @return
    */
   public TeamStatus getStatus() {
      return status;
   }
}
