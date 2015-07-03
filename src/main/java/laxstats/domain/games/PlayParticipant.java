package laxstats.domain.games;

import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayRole;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

/**
 * {@code PlayParticipant} represents the domain object model of a player responsible for or
 * directly involved in a play.
 */
public class PlayParticipant extends AbstractAnnotatedEntity {
   private String id;
   private String playId;
   private String attendeeId;
   private String teamSeasonId;
   private PlayRole role;
   private boolean pointCredit;
   private int cumulativeAssists;
   private int cumulativeGoals;

   /**
    * Creates a {@code PlayParticipant} with the given information.
    *
    * @param id
    * @param playId
    * @param attendeeId
    * @param teamSeasonId
    * @param role
    * @param pointCredit
    * @param cumulativeAssists
    * @param cumulativeGoals
    */
   public PlayParticipant(String id, String playId, String attendeeId, String teamSeasonId,
      PlayRole role, boolean pointCredit, int cumulativeAssists, int cumulativeGoals) {
      this.id = id;
      this.playId = playId;
      this.attendeeId = attendeeId;
      this.teamSeasonId = teamSeasonId;
      this.role = role;
      this.pointCredit = pointCredit;
      this.cumulativeAssists = cumulativeAssists;
      this.cumulativeGoals = cumulativeGoals;
   }

   /**
    * Creates an empty ({@code PlayParticipant}. Internal use only.
    */
   protected PlayParticipant() {
   }

   /**
    * Updates the play participant with the given information.
    *
    * @param dto
    */
   public void update(PlayParticipantDTO dto) {
      attendeeId = dto.getAttendee().getId();
      teamSeasonId = dto.getTeamSeason().getId();
      pointCredit = dto.isPointCredit();
      cumulativeAssists = dto.getCumulativeAssists();
      cumulativeGoals = dto.getCumulativeGoals();
   }

   /**
    * Returns the play participant's unique identifier.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the play identifier.
    *
    * @return
    */
   public String getPlayId() {
      return playId;
   }

   /**
    * Returns the participant's game attendee identifier.
    *
    * @return
    */
   public String getAttendeeId() {
      return attendeeId;
   }

   /**
    * Returns the participant's team identifier.
    *
    * @return
    */
   public String getSeasonTeamId() {
      return teamSeasonId;
   }

   /**
    * Returns the participant's role in the play.
    *
    * @return
    */
   public PlayRole getRole() {
      return role;
   }

   /**
    * Returns trus if this play represents a point credit for the participant, false otherwise.
    *
    * @return
    */
   public boolean isPointCredit() {
      return pointCredit;
   }

   /**
    * Returns the cumulative assists earned by this participant before this play.
    *
    * @return
    */
   public int getCumulativeAssists() {
      return cumulativeAssists;
   }

   /**
    * Returns the cumulative goals earned by this participant before this play.
    *
    * @return
    */
   public int getCumulativeGoals() {
      return cumulativeGoals;
   }
}
