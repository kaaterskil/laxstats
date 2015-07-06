package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

import org.joda.time.Period;

/**
 * {@code PenaltyResource} contains user-defined information with which to create and update penalty
 * plays.
 */
public class PenaltyResourceImpl extends AbstractPlayResource implements PenaltyResource {
   @NotNull
   private String elapsedTime;

   @NotNull
   private String committedById;

   private String committedAgainstId;

   @NotNull
   private String violationId;

   @NotNull
   private String duration;

   /**
    * Creates a {@code PenaltyResource} from the given information.
    *
    * @param playId
    * @param gameId
    * @param teamSeasonId
    * @param period
    * @param comment
    * @param teamName
    * @param elapsedTime
    * @param committedById
    * @param committedAgainstId
    * @param violationId
    * @param duration
    */
   public PenaltyResourceImpl(String playId, String gameId, String teamSeasonId, int period,
      String comment, String teamName, String elapsedTime, String committedById,
      String committedAgainstId, String violationId, String duration) {
      super(playId, PlayType.PENALTY, PlayKey.PLAY, gameId, teamSeasonId, period, comment, teamName);
      this.elapsedTime = elapsedTime;
      this.committedById = committedById;
      this.committedAgainstId = committedAgainstId;
      this.violationId = violationId;
      this.duration = duration;
   }

   /**
    * Creates an empty {@code PenaltyResource}.
    */
   public PenaltyResourceImpl() {
      super(PlayType.PENALTY, PlayKey.PLAY);
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
   public String getCommittedById() {
      return committedById;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCommittedById(String committedById) {
      this.committedById = committedById;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getCommittedAgainstId() {
      return committedAgainstId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCommittedAgainstId(String committedAgainstId) {
      this.committedAgainstId = committedAgainstId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getViolationId() {
      return violationId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setViolationId(String violationId) {
      this.violationId = violationId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getDuration() {
      return duration;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDuration(String duration) {
      this.duration = duration;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Period getDurationAsPeriod() {
      return Period.parse(duration);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDuration(Period duration) {
      this.duration = duration.toString();
   }

}
