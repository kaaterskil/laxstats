package laxstats.web.games;

import laxstats.api.games.PlayKey;

public interface PlayResource {

   /**
    * Returns the play's unique identifier, or null if the play has not been persisted.
    *
    * @return
    */
   public String getPlayId();

   /**
    * Sets the play's unique identifier. Use null if the play has not been persisted.
    *
    * @param playId
    */
   public void setPlayId(String playId);

   /**
    * Returns the play class type.
    *
    * @return
    */
   public String getPlayType();

   /**
    * Returns the major play category. Never null.
    *
    * @return
    */
   public PlayKey getPlayKey();

   /**
    * Returns the identifier of the associated game. Never null.
    *
    * @return
    */
   public String getGameId();

   /**
    * Sets the identifier of the associated game. Must not be null.
    *
    * @param gameId
    */
   public void setGameId(String gameId);

   /**
    * Returns the identifier of the associated team season. Never null.
    *
    * @return
    */
   public String getTeamSeasonId();

   /**
    * Sets the identifier of the associated team season. Must not be null.
    *
    * @param teamSeasonId
    */
   public void setTeamSeasonId(String teamSeasonId);

   /**
    * Returns the play period.
    *
    * @return
    */
   public int getPeriod();

   /**
    * Sets the play period.
    *
    * @param period
    */
   public void setPeriod(int period);

   /**
    * Returns comments, or null.
    *
    * @return
    */
   public String getComment();

   /**
    * Sets a comment. Use null if none.
    *
    * @param comment
    */
   public void setComment(String comment);

   /**
    * Returns the team name.
    *
    * @return
    */
   public String getTeamName();

   /**
    * Sets the team name.
    *
    * @param teamName
    */
   public void setTeamName(String teamName);

}
