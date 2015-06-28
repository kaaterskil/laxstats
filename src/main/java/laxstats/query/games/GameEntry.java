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
import laxstats.query.sites.SiteEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

@Entity
@Table(name = "games", indexes = { @Index(name = "event_idx1", columnList = "schedule"),
   @Index(name = "event_idx2", columnList = "startsAt"),
   @Index(name = "event_idx3", columnList = "alignment"),
   @Index(name = "event_idx4", columnList = "status"),
   @Index(name = "event_idx5", columnList = "conditions") })
public class GameEntry implements Serializable {
   private static final long serialVersionUID = 3864013780705615870L;

   @Id
   @Column(length = 36)
   private String id;

   @ManyToOne
   @JoinColumn(name = "site_id")
   private SiteEntry site;

   @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private SiteAlignment alignment;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime startsAt;

   @Enumerated(EnumType.STRING)
   @Column(length = 20, nullable = false)
   private Schedule schedule;

   @Enumerated(EnumType.STRING)
   @Column(length = 20, nullable = false)
   private Status status;

   @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private Conditions conditions;

   @Column(nullable = false)
   private String description;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
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

   /*---------- Methods ----------*/

   public AttendeeEntry getAttendee(String key) {
      return eventAttendees.get(key);
   }

   public void addPlay(PlayEntry play) {
      play.setEvent(this);
      plays.put(play.getId(), play);
   }

   public void deletePlay(PlayEntry play) {
      play.clear();
      plays.remove(play.getId());
   }

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
         if (each.getTeam().getId().equals(teamSeasonId)) {
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
         if (each.getTeam().getId().equals(teamSeasonId)) {
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
            if (attendee.getTeamSeason().equals(tse)) {
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

            if (tse.getId().equals(teamSeason.getId())) {
               final List<AttendeeEntry> list = new ArrayList<AttendeeEntry>();
               for (final AttendeeEntry attendee : eventAttendees.values()) {
                  if (attendee.getTeamSeason().equals(tse)) {
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
    * Returns the event's home team. If the event does not have a home or visiting team designation,
    * the method will return the event's first team.
    *
    * @return
    */
   public TeamSeasonEntry getHomeTeam() {
      if (teams.size() == 0) {
         return null;
      }
      else if (teams.size() == 2 && teams.get(1).getAlignment().equals(Alignment.HOME)) {
         return teams.get(1).getTeamSeason();
      }
      return teams.get(0).getTeamSeason();
   }

   public TeamEvent getHomeTeamEvent() {
      if (teams.size() == 0) {
         return null;
      }
      else if (teams.size() == 2 && teams.get(1).getAlignment().equals(Alignment.HOME)) {
         return teams.get(1);
      }
      return teams.get(0);
   }

   /**
    * Returns the event's visiting team. If the event does not have a home or visiting team
    * designation, the method will return the event's second team. If no teams have been assigned
    *
    * @return
    */
   public TeamSeasonEntry getVisitingTeam() {
      if (teams.size() < 2) {
         return null;
      }
      else if (!teams.get(0).getAlignment().equals(Alignment.HOME)) {
         return teams.get(0).getTeamSeason();
      }
      return teams.get(1).getTeamSeason();
   }

   public TeamEvent getVisitingTeamEvent() {
      if (teams.size() == 0) {
         return null;
      }
      else if (!teams.get(0).getAlignment().equals(Alignment.HOME)) {
         return teams.get(0);
      }
      return teams.get(1);
   }

   public TeamSeasonEntry getTeam(String teamSeasonId) {
      for (final TeamEvent te : teams) {
         final TeamSeasonEntry tse = te.getTeamSeason();
         if (tse.getId().equals(teamSeasonId)) {
            return tse;
         }
      }
      return null;
   }

   /* ---------- Getter/Setters ---------- */

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public SiteEntry getSite() {
      return site;
   }

   public void setSite(SiteEntry site) {
      this.site = site;
   }

   public SiteAlignment getAlignment() {
      return alignment;
   }

   public void setAlignment(SiteAlignment alignment) {
      this.alignment = alignment;
   }

   public LocalDateTime getStartsAt() {
      return startsAt;
   }

   public void setStartsAt(LocalDateTime startsAt) {
      this.startsAt = startsAt;
   }

   public Schedule getSchedule() {
      return schedule;
   }

   public void setSchedule(Schedule schedule) {
      this.schedule = schedule;
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   public Conditions getConditions() {
      return conditions;
   }

   public void setConditions(Conditions conditions) {
      this.conditions = conditions;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   public UserEntry getCreatedBy() {
      return createdBy;
   }

   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }

   public Map<String, AttendeeEntry> getEventAttendees() {
      return eventAttendees;
   }

   public List<TeamEvent> getTeams() {
      return teams;
   }

   public Map<String, PlayEntry> getPlays() {
      return plays;
   }

   private static class AttendeeComparator implements Comparator<AttendeeEntry> {
      @Override
      public int compare(AttendeeEntry o1, AttendeeEntry o2) {
         final String l1 = o1.label();
         final String l2 = o2.label();
         return l1.compareToIgnoreCase(l2);
      }
   }

   private static class PenaltyComparator implements Comparator<PenaltyEntry> {
      @Override
      public int compare(PenaltyEntry o1, PenaltyEntry o2) {
         final Seconds t1 =
            PlayUtils.getTotalElapsedTime(o1.getPeriod(), o1.getElapsedTime()).toStandardSeconds();
         final Seconds t2 =
            PlayUtils.getTotalElapsedTime(o2.getPeriod(), o2.getElapsedTime()).toStandardSeconds();
         final int s1 = t1.getSeconds();
         final int s2 = t2.getSeconds();
         return s1 > s2 ? 1 : s1 < s2 ? -1 : 0;
      }
   }

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
            final int s1 = o1.getElapsedTime().toStandardSeconds().getSeconds();
            final int s2 = o2.getElapsedTime().toStandardSeconds().getSeconds();
            return s1 < s2 ? -1 : (s1 > s2 ? 1 : 0);
         }

         final int i1 = o1.getSequenceNumber();
         final int i2 = o2.getSequenceNumber();
         return i1 < i2 ? -1 : (i1 > i2 ? 1 : 0);
      }
   }
}
