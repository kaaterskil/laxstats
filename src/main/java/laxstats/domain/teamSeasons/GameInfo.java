package laxstats.domain.teamSeasons;

import java.io.Serializable;

import org.joda.time.LocalDateTime;

/**
 * {@code GameInfo} is a value object representing a game within this team season aggregate.
 */
public class GameInfo implements Serializable, Comparable<GameInfo> {
   private static final long serialVersionUID = 3443036108129483962L;

   private final String eventId;
   private final String siteId;
   private final String teamOneId;
   private final String teamTwoId;
   private final LocalDateTime startsAt;

   /**
    * Creates a {@code GameInfo} with the given information.
    *
    * @param eventId
    * @param siteId
    * @param teamOneId
    * @param teamTwoId
    * @param startsAt
    */
   public GameInfo(String eventId, String siteId, String teamOneId, String teamTwoId,
      LocalDateTime startsAt) {
      this.eventId = eventId;
      this.siteId = siteId;
      this.teamOneId = teamOneId;
      this.teamTwoId = teamTwoId;
      this.startsAt = startsAt;
   }

   /**
    * Returns the unique identifier of this value object.
    * 
    * @return
    */
   public String getEventId() {
      return eventId;
   }

   /**
    * Returns the identifier of the site aggregate.
    * 
    * @return
    */
   public String getSiteId() {
      return siteId;
   }

   /**
    * Returns the identifier of the first team.
    * 
    * @return
    */
   public String getTeamOneId() {
      return teamOneId;
   }

   /**
    * Returns the identifier of the second team.
    * 
    * @return
    */
   public String getTeamTwoId() {
      return teamTwoId;
   }

   /**
    * Returns the scheduled date and time of this game.
    * 
    * @return
    */
   public LocalDateTime getStartsAt() {
      return startsAt;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return eventId.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (obj != null && obj instanceof GameInfo) {
         final GameInfo that = (GameInfo)obj;
         return eventId.equals(that.eventId);
      }
      return false;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int compareTo(GameInfo o) {
      return startsAt.isBefore(o.startsAt) ? -1 : (startsAt.equals(o.startsAt) ? 0 : 1);
   }
}
