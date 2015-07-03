package laxstats.domain.games;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayUtils;
import laxstats.api.games.ScoreAttemptType;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;
import org.axonframework.eventsourcing.annotation.EventSourcedMember;
import org.joda.time.Period;

/**
 * {@code Play} represents the base domain model object of all plays. It is abstract and must be
 * sub-classed to be instantiated.
 */
public abstract class Play extends AbstractAnnotatedEntity {
   protected String id;
   protected String discriminator;
   protected PlayKey playKey;
   protected String eventId;
   protected String teamId;
   protected int period;
   protected Period elapsedTime;
   protected ScoreAttemptType scoreAttemptType;
   protected PlayResult result;
   protected String comment;

   @EventSourcedMember
   protected final List<PlayParticipant> participants = new ArrayList<>();

   /**
    * Creates a {@code Play} with the given information.
    *
    * @param id
    * @param discriminator
    * @param playKey
    * @param eventId
    * @param teamId
    * @param period
    * @param elapsedTime
    * @param scoreAttemptType
    * @param result
    * @param comment
    * @param participants
    */
   protected Play(String id, String discriminator, PlayKey playKey, String eventId, String teamId,
      int period, Period elapsedTime, ScoreAttemptType scoreAttemptType, PlayResult result,
      String comment, List<PlayParticipantDTO> participants) {
      this.id = id;
      this.discriminator = discriminator;
      this.playKey = playKey;
      this.eventId = eventId;
      this.teamId = teamId;
      this.period = period;
      this.elapsedTime = elapsedTime;
      this.scoreAttemptType = scoreAttemptType;
      this.result = result;
      this.comment = comment;

      if (participants != null) {
         for (final PlayParticipantDTO dto : participants) {
            final String attendeeId = dto.getAttendee().getId();
            final PlayParticipant entity =
               new PlayParticipant(dto.getId(), id, attendeeId, teamId, dto.getRole(), dto
                  .isPointCredit(), dto.getCumulativeAssists(), dto.getCumulativeGoals());
            this.participants.add(entity);
         }
      }
   }

   /**
    * Creates an empty {@code Play}. Internal use only.
    */
   protected Play() {
   }

   /**
    * Returns the period of time between the play and the start of the game.
    *
    * @return
    */
   public Period getTotalElapsedTime() {
      return PlayUtils.getTotalElapsedTime(period, elapsedTime);
   }

   /**
    * Updates the given play participants matching the identifiers contained in the information in
    * the given list.
    *
    * @param participant The play participant to update.
    * @param list A collection of updated participant information.
    */
   protected void updateParticipant(PlayParticipant participant, List<PlayParticipantDTO> list) {
      for (final PlayParticipantDTO dto : list) {
         if (dto.getRole().equals(participant.getRole())) {
            participant.update(dto);
         }
      }
   }

   /**
    * Returns the unique identifier of the play.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the class type of the play.
    *
    * @return
    */
   public String getDiscriminator() {
      return discriminator;
   }

   /**
    * Returns the major play category.
    *
    * @return
    */
   public PlayKey getPlayKey() {
      return playKey;
   }

   /*
    * Returns the game identifier.
    */
   public String getEventId() {
      return eventId;
   }

   /**
    * Returns the identifier of the team responsible for the play.
    *
    * @return
    */
   public String getTeamId() {
      return teamId;
   }

   /**
    * Returns the play period in which the play occurred.
    *
    * @return
    */
   public int getPeriod() {
      return period;
   }

   /**
    * Returns the period of time between the beginning of the play period and the play.
    *
    * @return
    */
   public Period getElapsedTime() {
      return elapsedTime;
   }

   /**
    * Returns the type of score attempt, or null if not a shot or goal.
    *
    * @return
    */
   public ScoreAttemptType getScoreAttemptType() {
      return scoreAttemptType;
   }

   /**
    * Returns the result of the play.
    *
    * @return
    */
   public PlayResult getResult() {
      return result;
   }

   /**
    * Returns play comments, or null if none.
    *
    * @return
    */
   public String getComment() {
      return comment;
   }

   /**
    * Returns the list of participants in the play.
    *
    * @return
    */
   public List<PlayParticipant> getParticipants() {
      return participants;
   }
}
