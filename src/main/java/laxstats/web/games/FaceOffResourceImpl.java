package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

import org.joda.time.Period;

/**
 * {@code FaceOffResource} represents a face-off resource for remote clients.
 */
public class FaceOffResourceImpl extends AbstractPlayResource implements FaceOffResource {
   @NotNull
   private String elapsedTime;

   @NotNull
   private String winnerId;

   @NotNull
   private String loserId;

   /**
    * Creates a {@code FaceOffResource} with the given information.
    *
    * @param playId
    * @param gameId
    * @param teamSeasonId
    * @param period
    * @param comment
    * @param teamName
    * @param elapsedTime
    * @param winnerId
    * @param loserId
    */
   public FaceOffResourceImpl(String playId, String gameId, String teamSeasonId, int period,
      String comment, String teamName, String elapsedTime, String winnerId, String loserId) {
      super(playId, PlayType.FACEOFF, PlayKey.PLAY, gameId, teamSeasonId, period, comment, teamName);
      this.elapsedTime = elapsedTime;
      this.winnerId = winnerId;
      this.loserId = loserId;
   }

   /**
    * Creates an empty {@code FaceOffResource}.
    */
   public FaceOffResourceImpl() {
      super(PlayType.FACEOFF, PlayKey.PLAY);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getElapsedTime() {
      return elapsedTime;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setElapsedTime(String elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Period getElapsedTimeAsPeriod() {
      return Period.parse(elapsedTime);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getWinnerId() {
      return winnerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setWinnerId(String winnerId) {
      assert winnerId != null;
      this.winnerId = winnerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getLoserId() {
      return loserId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLoserId(String loserId) {
      this.loserId = loserId;
   }

}
