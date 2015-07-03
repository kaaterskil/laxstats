package laxstats.domain.games;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laxstats.api.games.PenaltyUpdated;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayType;
import laxstats.api.games.PlayUtils;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

/**
 * {@code Penalty} represents the domain object model of the penalty imposed on a player for the
 * commission of a rule violation.
 */
public class Penalty extends Play {
   private LocalDateTime eventStart;
   private String violationId;
   private Period duration;

   /**
    * Creates a {@code Penalty} with the given information.
    *
    * @param id
    * @param eventId
    * @param teamId
    * @param eventStart
    * @param period
    * @param elapsedTime
    * @param violationId
    * @param duration
    * @param comment
    * @param participants
    */
   public Penalty(String id, String eventId, String teamId, LocalDateTime eventStart, int period,
      Period elapsedTime, String violationId, Period duration, String comment,
      List<PlayParticipantDTO> participants) {
      super(id, PlayType.PENALTY, PlayKey.PLAY, eventId, teamId, period, elapsedTime, null, null,
         comment, participants);
      this.eventStart = eventStart;
      this.violationId = violationId;
      this.duration = duration;
   }

   /**
    * Creates an empty {@code Penalty}. Internal use only.
    */
   protected Penalty() {
      super();
   }

   /**
    * Returns a list of participant identifiers.
    *
    * @return
    */
   public List<String> getParticipantIds() {
      final List<String> list = new ArrayList<>();
      for (final PlayParticipant participant : participants) {
         list.add(participant.getAttendeeId());
      }
      return list;
   }

   /**
    * Returns the time interval that the participant who committed the violation may be sidelined,
    * with respect to the start of the game.
    *
    * @return
    */
   public Interval getInterval() {
      return PlayUtils.getPenaltyInterval(eventStart, period, elapsedTime, duration);
   }

   /**
    * Updates and persists changes to the state of an existing penalty with information contained in
    * the given event. This event handler is delegated from the aggregate.
    *
    * @param event
    */
   @EventHandler
   protected void handle(PenaltyUpdated event) {
      if (!event.getPlayId().equals(id)) {
         return;
      }

      final PlayDTO dto = event.getPlayDTO();
      teamId = dto.getTeam().getId();
      period = dto.getPeriod();
      elapsedTime = dto.getElapsedTime();
      comment = dto.getComment();

      eventStart = dto.getEvent().getStartsAt();
      violationId = dto.getViolation().getId();
      duration = dto.getPenaltyDuration();

      // Update existing committedBy and committedAgainst
      updateParticipants(dto);
   }

   /**
    * Updates the participants with the given information. This method will delete participants that
    * have been reassigned, add newly designated participants, and/or otherwise update the state of
    * existing participants.
    *
    * @param dto
    */
   private void updateParticipants(PlayDTO dto) {
      // First remove any deleted participants
      removeParticipant(dto);

      for (final PlayParticipantDTO pdto : dto.getParticipants()) {
         // Update existing participants
         boolean found = false;
         for (final PlayParticipant p : getParticipants()) {
            if (p.getId().equals(pdto.getId())) {
               found = true;
               p.update(pdto);
            }
         }
         // Create new participant (penalty against)
         if (!found) {
            final PlayParticipant entity =
               new PlayParticipant(pdto.getId(), id, pdto.getAttendee().getId(), pdto
                  .getTeamSeason().getId(), pdto.getRole(), pdto.isPointCredit(), pdto
                  .getCumulativeAssists(), pdto.getCumulativeGoals());
            participants.add(entity);
         }
      }
   }

   /**
    * Removes the participants matching the identifiers in the given information.
    *
    * @param dto
    */
   private void removeParticipant(PlayDTO dto) {
      final Iterator<PlayParticipant> iter = getParticipants().iterator();
      while (iter.hasNext()) {
         final PlayParticipant p = iter.next();
         boolean found = false;
         for (final PlayParticipantDTO pdto : dto.getParticipants()) {
            if (p.getId().equals(pdto.getId())) {
               found = true;
            }
         }
         if (!found) {
            iter.remove();
         }
      }
   }

   /**
    * Returns the identifier of the associated violation.
    *
    * @return
    */
   public String getViolationId() {
      return violationId;
   }

   /**
    * Returns the duration of the penalty as a time period.
    *
    * @return
    */
   public Period getDuration() {
      return duration;
   }

}
