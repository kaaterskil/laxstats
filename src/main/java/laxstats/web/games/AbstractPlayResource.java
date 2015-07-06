package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;

/**
 * {@code AbstractPlayResource} is the base class for all play resources.
 */
public abstract class AbstractPlayResource implements PlayResource {

   protected String playId;
   protected final String playType;
   protected final PlayKey playKey;

   @NotNull
   protected String gameId;

   @NotNull
   protected String teamSeasonId;

   protected int period = 0;
   protected String comment;
   protected String teamName;

   /**
    * Creates an {@code AbstractPlayResource} with the given information.
    *
    * @param playId
    * @param playType
    * @param playKey
    * @param gameId
    * @param teamSeasonId
    * @param period
    * @param comment
    * @param teamName
    */
   protected AbstractPlayResource(String playId, String playType, PlayKey playKey, String gameId,
      String teamSeasonId, int period, String comment, String teamName) {
      this.playId = playId;
      this.playType = playType;
      this.playKey = playKey;
      this.gameId = gameId;
      this.teamSeasonId = teamSeasonId;
      this.period = period;
      this.comment = comment;
      this.teamName = teamName;
   }

   /**
    * Creates an {@code AbstractPlayResource} with the given class type and category.
    *
    * @param playType
    * @param playKey
    */
   protected AbstractPlayResource(String playType, PlayKey playKey) {
      this.playType = playType;
      this.playKey = playKey;
   }

   /**
    * Returns the play's unique identifier, or null if the play has not been persisted.
    *
    * @return
    */
   @Override
   public String getPlayId() {
      return playId;
   }

   /**
    * Sets the play's unique identifier. Use null if the play has not been persisted.
    *
    * @param playId
    */
   @Override
   public void setPlayId(String playId) {
      this.playId = playId;
   }

   /**
    * Returns the play class type.
    *
    * @return
    */
   @Override
   public String getPlayType() {
      return playType;
   }

   /**
    * Returns the major play category. Never null.
    *
    * @return
    */
   @Override
   public PlayKey getPlayKey() {
      return playKey;
   }

   /**
    * Returns the identifier of the associated game. Never null.
    *
    * @return
    */
   @Override
   public String getGameId() {
      return gameId;
   }

   /**
    * Sets the identifier of the associated game. Must not be null.
    *
    * @param gameId
    */
   @Override
   public void setGameId(String gameId) {
      assert gameId != null;
      this.gameId = gameId;
   }

   /**
    * Returns the identifier of the associated team season. Never null.
    *
    * @return
    */
   @Override
   public String getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Sets the identifier of the associated team season. Must not be null.
    *
    * @param teamSeasonId
    */
   @Override
   public void setTeamSeasonId(String teamSeasonId) {
      assert teamSeasonId != null;
      this.teamSeasonId = teamSeasonId;
   }

   /**
    * Returns the play period.
    *
    * @return
    */
   @Override
   public int getPeriod() {
      return period;
   }

   /**
    * Sets the play period.
    *
    * @param period
    */
   @Override
   public void setPeriod(int period) {
      this.period = period;
   }

   /**
    * Returns comments, or null.
    *
    * @return
    */
   @Override
   public String getComment() {
      return comment;
   }

   /**
    * Sets a comment. Use null if none.
    *
    * @param comment
    */
   @Override
   public void setComment(String comment) {
      this.comment = comment;
   }

   /**
    * Returns the team name.
    *
    * @return
    */
   @Override
   public String getTeamName() {
      return teamName;
   }

   /**
    * Sets the team name.
    *
    * @param teamName
    */
   @Override
   public void setTeamName(String teamName) {
      this.teamName = teamName;
   }

}
