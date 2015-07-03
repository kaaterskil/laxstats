package laxstats.domain.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayRole;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.PenaltyEntry;
import laxstats.query.games.PlayParticipantEntry;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Seconds;

/**
 * {@code PlayServiceImpl} provides a concrete base implementation of common domain functions across
 * various plays. It is not intended to be instantiated directly, except through subclasses.
 */
public class PlayServiceImpl implements PlayService {
   private final Game game;

   protected PlayServiceImpl(Game game) {
      this.game = game;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public final Game getEvent() {
      return game;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List<Play> getPlays(String discriminator) {
      final List<Play> list = new ArrayList<>();
      for (final Play each : game.getPlays().values()) {
         if (discriminator == null || each.getDiscriminator().equals(discriminator)) {
            list.add(each);
         }
      }
      Collections.sort(list, new ElapsedTimeComparator());
      return list;
   }

   /**
    * Returns true if the play with the identifier in the given information can be created, false
    * otherwise. Tests include whether the play already exists, whether the play participants are
    * registered to play in the game, and whether any player was sidelined as the result of a
    * penalty at the time of the play.
    */
   @Override
   public boolean canRecordPlay(PlayDTO dto) {
      if (playRecorded(dto)) {
         return false;
      }
      if (!participantsRegistered(dto)) {
         return false;
      }
      if (participantsSidelined(dto)) {
         return false;
      }
      return true;
   }

   /**
    * Returns true if the play matching the identifier in the given information can by updated,
    * false otherwise. Tests include whether the play already exists, whether the play participants
    * are registered to play in the game, and whether any player was sidelined as the result of a
    * penalty at the time of the play.
    */
   @Override
   public boolean canUpdatePlay(PlayDTO dto) {
      if (!playRecorded(dto)) {
         return false;
      }
      if (!participantsRegistered(dto)) {
         return false;
      }
      if (participantsSidelined(dto)) {
         return false;
      }
      return true;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean playRecorded(PlayDTO dto) {
      final String key = dto.getIdentifier().toString();
      return getEvent().getPlays().containsKey(key);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean participantsRegistered(PlayDTO dto) {
      for (final PlayParticipantDTO participantDTO : dto.getParticipants()) {
         if (!participantRegistered(participantDTO)) {
            return false;
         }
      }
      return true;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setInvariants(PlayDTO dto) {
      // Noop
   }

   /**
    * Returns true if the play participant represented by the given DTO is registered to play in the
    * given game, false otherwise.
    *
    * @param dto
    * @return
    */
   protected final boolean participantRegistered(PlayParticipantDTO dto) {
      final String attendeeId = dto.getAttendee().getId();
      return getEvent().getAttendees().containsKey(attendeeId);
   }

   /**
    * Returns true if the a participant in the given play was sidelined as the result of a penalty
    * at the time of the play and is therefore invalid, false otherwise.
    *
    * @param dto
    * @return
    */
   protected boolean participantsSidelined(PlayDTO dto) {
      final DateTime instant = dto.getInstant();
      for (final PenaltyEntry penalty : dto.getEvent().getPenalties()) {
         final Interval interval = penalty.getInterval();
         final boolean overlaps = interval.contains(instant);
         final boolean concurrent = interval.getStart().equals(instant);

         if (overlaps && !concurrent) {
            for (final PlayParticipantDTO pdto : dto.getParticipants()) {
               final AttendeeEntry target = pdto.getAttendee();

               for (final PlayParticipantEntry p : penalty.getParticipants()) {
                  final AttendeeEntry attendee = p.getAttendee();
                  final PlayRole role = p.getRole();

                  if (attendee.equals(target) && role.equals(PlayRole.PENALTY_COMMITTED_BY)) {
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }

   /**
    * Class object used to sort plays by elapsed time.
    */
   private static class ElapsedTimeComparator implements Comparator<Play> {
      @Override
      public int compare(Play o1, Play o2) {
         final Seconds t1 = o1.getTotalElapsedTime().toStandardSeconds();
         final Seconds t2 = o2.getTotalElapsedTime().toStandardSeconds();
         return t1.getSeconds() < t2.getSeconds() ? -1 : (t1.getSeconds() > t2.getSeconds() ? 1 : 0);
      }
   }

}
