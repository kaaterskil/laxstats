package laxstats.query.games;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import laxstats.api.games.Alignment;
import laxstats.api.games.Conditions;
import laxstats.api.games.PlayUtils;
import laxstats.api.games.Schedule;
import laxstats.api.games.Status;
import laxstats.api.sites.SiteAlignment;
import laxstats.api.utils.Constants;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

/**
 * {@code GameEntry} represents the query object model of a game.
 */
@Entity
@Table(name = "games", indexes = { @Index(name = "event_idx1", columnList = "schedule"),
   @Index(name = "event_idx2", columnList = "startsAt"),
   @Index(name = "event_idx3", columnList = "alignment"),
   @Index(name = "event_idx4", columnList = "status"),
   @Index(name = "event_idx5", columnList = "conditions") })
public class GameEntry implements Serializable {
   private static final long serialVersionUID = 3864013780705615870L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @ManyToOne
   @JoinColumn(name = "site_id")
   private SiteEntry site;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private SiteAlignment alignment;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime startsAt;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private Schedule schedule;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private Status status;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private Conditions conditions;

   @Column
   private String description;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
   @MapKeyColumn(name = "id")
   private final Map<String, AttendeeEntry> eventAttendees = new HashMap<>();

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
   private final List<TeamEvent> teams = new ArrayList<>();

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
   @MapKeyColumn(name = "id")
   private final Map<String, PlayEntry> plays = new HashMap<>();

   /**
    * Returns the game attendee matching the given unique identifier, or null.
    *
    * @param key
    * @return
    */
   public AttendeeEntry getAttendee(String key) {
      return eventAttendees.get(key);
   }

   /**
    * Adds the given play to collection of plays for this game.
    *
    * @param play
    */
   public void addPlay(PlayEntry play) {
      play.setEvent(this);
      plays.put(play.getId(), play);
   }

   /**
    * Deletes the given play from the collection of plays for this game.
    *
    * @param play
    */
   public void deletePlay(PlayEntry play) {
      play.clear();
      plays.remove(play.getId());
   }

   /**
    * Returns a list of the penalties handed down during the game, in chronological order.
    *
    * @return
    */
   public List<PenaltyEntry> getPenalties() {
      final List<PenaltyEntry> list = new ArrayList<>();
      for (final PlayEntry play : plays.values()) {
         if (play instanceof PenaltyEntry) {
            list.add((PenaltyEntry)play);
         }
      }
      Collections.sort(list, new PenaltyComparator());
      return list;
   }

   /**
    * Returns a list of home team plays sorted by period/elapsed time.
    *
    * @return
    */
   public List<PlayEntry> getHomePlays() {
      final String teamSeasonId = getHomeTeam().getId();

      final List<PlayEntry> list = new ArrayList<>();
      for (final PlayEntry each : getPlays().values()) {
         if (each.getTeam()
            .getId()
            .equals(teamSeasonId)) {
            list.add(each);
         }
      }
      Collections.sort(list, new PlayComparator());
      return list;
   }

   /**
    * Returns a list of visiting team plays sorted by period/elapsed time.
    *
    * @return
    */
   public List<PlayEntry> getVisitorPlays() {
      final String teamSeasonId = getVisitingTeam().getId();

      final List<PlayEntry> list = new ArrayList<>();
      for (final PlayEntry each : getPlays().values()) {
         if (each.getTeam()
            .getId()
            .equals(teamSeasonId)) {
            list.add(each);
         }
      }
      Collections.sort(list, new PlayComparator());
      return list;
   }

   /**
    * Returns a collection of game attendees. The map keys are the team names and the map values are
    * sorted lists of attendees from each team roster.
    *
    * @return
    */
   public Map<String, List<AttendeeEntry>> getAttendees() {
      final Map<String, List<AttendeeEntry>> result = new HashMap<>();
      for (final TeamEvent teamEvent : teams) {
         final TeamSeasonEntry tse = teamEvent.getTeamSeason();

         final List<AttendeeEntry> list = new ArrayList<AttendeeEntry>();
         for (final AttendeeEntry attendee : eventAttendees.values()) {
            if (attendee.getTeamSeason()
               .equals(tse)) {
               list.add(attendee);
            }
         }
         Collections.sort(list, new AttendeeComparator());
         result.put(tse.getName(), list);
      }
      return result;
   }

   /**
    * Returns a collection of game attendees for the given team season. The map key is the team name
    * and the value collection is a sorted list of attendees from the team roster.
    *
    * @param teamSeason
    * @return
    */
   public Map<String, List<AttendeeEntry>> getAttendees(TeamSeasonEntry teamSeason) {
      final Map<String, List<AttendeeEntry>> result = new HashMap<>();

      if (teamSeason != null) {
         for (final TeamEvent teamEvent : teams) {
            final TeamSeasonEntry tse = teamEvent.getTeamSeason();

            if (tse.getId()
               .equals(teamSeason.getId())) {
               final List<AttendeeEntry> list = new ArrayList<AttendeeEntry>();
               for (final AttendeeEntry attendee : eventAttendees.values()) {
                  if (attendee.getTeamSeason()
                     .equals(tse)) {
                     list.add(attendee);
                  }
               }
               Collections.sort(list, new AttendeeComparator());
               result.put(tse.getName(), list);
            }
         }
      }
      return result;
   }

   /**
    * Returns the event's home team season, or null if no team has been assigned. If the event does
    * not have a home or visiting team designation, the method will return the event's first team.
    *
    * @return
    */
   public TeamSeasonEntry getHomeTeam() {
      if (teams.size() == 0) {
         return null;
      }
      else if (teams.size() == 2 && teams.get(1)
         .getAlignment()
         .equals(Alignment.HOME)) {
         return teams.get(1)
            .getTeamSeason();
      }
      return teams.get(0)
         .getTeamSeason();
   }

   /**
    * Returns the associated team event that corresponds to the visiting team, or null if no team
    * has been assigned. If the event does not have a home or visiting team designation, the method
    * will return the event's second team event.
    *
    * @return
    */
   public TeamEvent getHomeTeamEvent() {
      if (teams.size() == 0) {
         return null;
      }
      else if (teams.size() == 2 && teams.get(1)
         .getAlignment()
         .equals(Alignment.HOME)) {
         return teams.get(1);
      }
      return teams.get(0);
   }

   /**
    * Returns the event's visiting team season or null if no teams have been assigned. If the event
    * does not have a home or visiting team designation, the method will return the event's second
    * team.
    *
    * @return
    */
   public TeamSeasonEntry getVisitingTeam() {
      if (teams.size() < 2) {
         return null;
      }
      else if (!teams.get(0)
         .getAlignment()
         .equals(Alignment.HOME)) {
         return teams.get(0)
            .getTeamSeason();
      }
      return teams.get(1)
         .getTeamSeason();
   }

   /**
    * Returns the associated team event that corresponds to the visiting team, or null if no team
    * has been assigned. If the event does not have a home or visiting team designation, the method
    * will return the event's second team event.
    *
    * @return
    */
   public TeamEvent getVisitingTeamEvent() {
      if (teams.size() == 0) {
         return null;
      }
      else if (!teams.get(0)
         .getAlignment()
         .equals(Alignment.HOME)) {
         return teams.get(0);
      }
      return teams.get(1);
   }

   /**
    * Returns the associated team season that matches the given unique identifier.
    *
    * @param teamSeasonId
    * @return
    */
   public TeamSeasonEntry getTeam(String teamSeasonId) {
      for (final TeamEvent te : teams) {
         final TeamSeasonEntry tse = te.getTeamSeason();
         if (tse.getId()
            .equals(teamSeasonId)) {
            return tse;
         }
      }
      return null;
   }

   /**
    * Returns the primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the playing field, or null for TBD.
    *
    * @return
    */
   public SiteEntry getSite() {
      return site;
   }

   /**
    * Sets the playing field. Use null for TBD.
    *
    * @param site
    */
   public void setSite(SiteEntry site) {
      this.site = site;
   }

   /**
    * Returns the playing field alignment, or null for TBD.
    *
    * @return
    */
   public SiteAlignment getAlignment() {
      return alignment;
   }

   /**
    * Sets the playing field alignment. Use null for TBD.
    *
    * @param alignment
    */
   public void setAlignment(SiteAlignment alignment) {
      this.alignment = alignment;
   }

   /**
    * Returns the game date and starting time, or null for TBD.
    *
    * @return
    */
   public LocalDateTime getStartsAt() {
      return startsAt;
   }

   /**
    * Sets the date and starting time of the game. Use null for TBD.
    *
    * @param startsAt
    */
   public void setStartsAt(LocalDateTime startsAt) {
      this.startsAt = startsAt;
   }

   /**
    * Returns the game schedule. Never null.
    *
    * @return
    */
   public Schedule getSchedule() {
      return schedule;
   }

   /**
    * Sets the game schedule, e.g. Regular, Pre-season, etc. Must not be null.
    *
    * @param schedule
    */
   public void setSchedule(Schedule schedule) {
      assert schedule != null;
      this.schedule = schedule;
   }

   /**
    * Returns the game status. Never null.
    *
    * @return
    */
   public Status getStatus() {
      return status;
   }

   /**
    * Sets the game status. Must not be null.
    *
    * @param status
    */
   public void setStatus(Status status) {
      assert status != null;
      this.status = status;
   }

   /**
    * Returns the game time weather conditions, or null.
    *
    * @return
    */
   public Conditions getConditions() {
      return conditions;
   }

   /**
    * Sets the game time weather conditions. Use null for unknown.
    *
    * @param conditions
    */
   public void setConditions(Conditions conditions) {
      this.conditions = conditions;
   }

   /**
    * Returns the game description, or null.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Sets the game description. Use null for none.
    *
    * @param description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Returns the date and time this game was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this game was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this game.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this game.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this game was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this game was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this game.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this game.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }

   /**
    * Returns the collection of game attendees. Map keys are represented by the attendee's unique
    * identifier.
    *
    * @return
    */
   public Map<String, AttendeeEntry> getEventAttendees() {
      return eventAttendees;
   }

   /**
    * Returns the opposing teams in list form.
    *
    * @return
    */
   public List<TeamEvent> getTeams() {
      return teams;
   }

   /**
    * Returns the collection of plays. Map keys are represented by the unique identifier of the
    * play.
    *
    * @return
    */
   public Map<String, PlayEntry> getPlays() {
      return plays;
   }

   /**
    * Class object used to sort attendees.
    */
   private static class AttendeeComparator implements Comparator<AttendeeEntry> {
      @Override
      public int compare(AttendeeEntry o1, AttendeeEntry o2) {
         final String l1 = o1.label();
         final String l2 = o2.label();
         return l1.compareToIgnoreCase(l2);
      }
   }

   /**
    * Class object used to sort penalties by elapsed time between the beginning of the game and
    * occurrence of the violation.
    */
   private static class PenaltyComparator implements Comparator<PenaltyEntry> {
      @Override
      public int compare(PenaltyEntry o1, PenaltyEntry o2) {
         final Seconds t1 = PlayUtils.getTotalElapsedTime(o1.getPeriod(), o1.getElapsedTime())
            .toStandardSeconds();
         final Seconds t2 = PlayUtils.getTotalElapsedTime(o2.getPeriod(), o2.getElapsedTime())
            .toStandardSeconds();
         final int s1 = t1.getSeconds();
         final int s2 = t2.getSeconds();
         return s1 > s2 ? 1 : s1 < s2 ? -1 : 0;
      }
   }

   /**
    * Class object used to sort plays by the play period, elapsed time between the play and the
    * start of the period, and play sequence number.
    */
   private static class PlayComparator implements Comparator<PlayEntry> {
      @Override
      public int compare(PlayEntry o1, PlayEntry o2) {
         final int p1 = o1.getPeriod();
         final int p2 = o2.getPeriod();

         if (p1 < p2) {
            return -1;
         }
         else if (p1 > p2) {
            return 1;
         }
         else if (o1.getElapsedTime() != null && o2.getElapsedTime() != null) {
            final int s1 = o1.getElapsedTime()
               .toStandardSeconds()
               .getSeconds();
            final int s2 = o2.getElapsedTime()
               .toStandardSeconds()
               .getSeconds();
            return s1 < s2 ? -1 : (s1 > s2 ? 1 : 0);
         }

         final int i1 = o1.getSequenceNumber();
         final int i2 = o2.getSequenceNumber();
         return i1 < i2 ? -1 : (i1 > i2 ? 1 : 0);
      }
   }
}
