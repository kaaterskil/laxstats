package laxstats.query.teamSeasons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.teamSeasons.TeamStatus;
import laxstats.api.utils.Constants;
import laxstats.query.games.TeamEvent;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.players.PlayerEntry;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * {@code TeamSeasonEntry} represents a query object model of a team season, corresponding to a
 * many-to-many relationship between teams and seasons. Team seasons contain rosters of players,
 * along with competition history for the given time period.
 */
@Entity
@Table(name = "team_seasons",
         indexes = { @Index(name = "team_season_idx1", columnList = "id, team_id, season_id"),
            @Index(name = "team_season_idx2", columnList = "league_id"),
            @Index(name = "team_season_idx3", columnList = "startsOn"),
            @Index(name = "team_season_idx4", columnList = "startsOn, endsOn") },
         uniqueConstraints = { @UniqueConstraint(name = "team_season_uk1", columnNames = {
            "team_id", "season_id" }) })
public class TeamSeasonEntry implements Serializable {
   private static final long serialVersionUID = 7574720960777194343L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @ManyToOne
   @JoinColumn(name = "team_id", nullable = false)
   private TeamEntry team;

   @ManyToOne
   @JoinColumn(name = "season_id", nullable = false)
   private SeasonEntry season;

   @ManyToOne
   @JoinColumn(name = "league_id")
   private LeagueEntry league;

   @Column(nullable = false)
   @Type(type = Constants.LOCAL_DATE_DATABASE_TYPE)
   private LocalDate startsOn;

   @Type(type = Constants.LOCAL_DATE_DATABASE_TYPE)
   private LocalDate endsOn;

   @Column(length = 100)
   private String name;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private TeamStatus status;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   @OneToMany(mappedBy = "teamSeason")
   private final List<PlayerEntry> roster = new ArrayList<PlayerEntry>();

   @OneToMany(mappedBy = "teamSeason")
   private final Map<LocalDateTime, TeamEvent> events = new HashMap<>();

   /**
    * Returns a concatenation of the sponsor name, team season name and description. If the team
    * season name is null, the parent team title is returned.
    *
    * @return
    */
   public String getFullName() {
      if (name != null) {
         final StringBuilder sb = new StringBuilder();
         sb.append(team.getSponsor()).append(" ").append(name);
         if (season.getDescription() != "") {
            sb.append(" ").append(season.getDescription());
         }
         return sb.toString();
      }
      return team.getTitle();
   }

   /**
    * Returns the concatenated sponsor and team name.
    *
    * @return
    */
   public String getShortName() {
      if (name != null) {
         final StringBuilder sb = new StringBuilder();
         sb.append(team.getSponsor()).append(" ").append(name);
         return sb.toString();
      }
      return team.getTitle();
   }

   /**
    * Returns the season startand end dates as an {@code Interval}, with dates converted to
    * timestamps at the start of each day.
    *
    * @return
    */
   public Interval getSeasonInterval() {
      return new Interval(startsOn.toDateTimeAtStartOfDay(), endsOn.toDateTimeAtStartOfDay());
   }

   /**
    * Adds the given player to the team season roster.
    *
    * @param player
    * @return
    */
   public boolean addPlayerToRoster(PlayerEntry player) {
      player.setTeamSeason(this);
      return roster.add(player);
   }

   /**
    * Returns the player with the given primary key, or null if none found.
    *
    * @param id
    * @return
    */
   public PlayerEntry getPlayer(String id) {
      for (final PlayerEntry each : roster) {
         if (each.getId().equals(id)) {
            return each;
         }
      }
      return null;
   }

   /**
    * Returns a the player roster as a map, sorted by last name, first name, with the player's
    * primary key ad the map key.
    *
    * @return
    */
   public Map<String, PlayerEntry> getRosterData() {
      final Map<String, PlayerEntry> data = new HashMap<>();
      if (roster.size() > 0) {
         final List<PlayerEntry> list = new ArrayList<>(roster);
         Collections.sort(list, new PlayerComparator());
         for (final PlayerEntry each : list) {
            data.put(each.getId(), each);
         }
      }
      return data;
   }

   /**
    * Return true if this season encompasses the given date and time, false otherwise.
    *
    * @param datetime
    * @return
    */
   public boolean includes(LocalDateTime datetime) {
      final DateTime instant = datetime.toDateTime();
      final Interval interval = getSeasonInterval();
      return interval.contains(instant);
   }

   /**
    * Class object used to sort players on this roster. The sort method is alphabetical last name
    * and first name.
    */
   private static class PlayerComparator implements Comparator<PlayerEntry> {
      @Override
      public int compare(PlayerEntry o1, PlayerEntry o2) {
         final PersonEntry p1 = o1.getPerson();
         final PersonEntry p2 = o2.getPerson();
         final int result = p1.getLastName().compareToIgnoreCase(p2.getLastName());
         return result == 0 ? p1.getFirstName().compareToIgnoreCase(p2.getFirstName()) : result;
      }

   }

   /**
    * Returns the primary key of this team season.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the team season primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the parent team. Never null.
    *
    * @return
    */
   public TeamEntry getTeam() {
      return team;
   }

   /**
    * Sets the parent team.
    *
    * @param team
    */
   public void setTeam(TeamEntry team) {
      assert team != null;
      this.team = team;
   }

   /**
    * Returns the associated season. Never null.
    *
    * @return
    */
   public SeasonEntry getSeason() {
      return season;
   }

   /**
    * Sets the associated season.
    *
    * @param season
    */
   public void setSeason(SeasonEntry season) {
      assert season != null;
      this.season = season;
   }

   /**
    * Returns the associated league, or null if none.
    *
    * @return
    */
   public LeagueEntry getLeague() {
      return league;
   }

   /**
    * Sets the associated league.
    *
    * @param league
    */
   public void setLeague(LeagueEntry league) {
      this.league = league;
   }

   /**
    * Returns the team season name.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the team season name.
    *
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Returns the season start date. Never null.
    *
    * @return
    */
   public LocalDate getStartsOn() {
      return startsOn;
   }

   /**
    * Sets the season start date.
    *
    * @param startsOn
    */
   public void setStartsOn(LocalDate startsOn) {
      assert startsOn != null;
      this.startsOn = startsOn;
   }

   /**
    * Returns the season end date.
    *
    * @return
    */
   public LocalDate getEndsOn() {
      return endsOn;
   }

   /**
    * Sets the season end date.
    *
    * @param endsOn
    */
   public void setEndsOn(LocalDate endsOn) {
      this.endsOn = endsOn;
   }

   /**
    * Returns the team season status. Never null.
    *
    * @return
    */
   public TeamStatus getStatus() {
      return status;
   }

   /**
    * Sets the status of this team season.
    *
    * @param status
    */
   public void setStatus(TeamStatus status) {
      assert status != null;
      this.status = status;
   }

   /**
    * Retirns the date and time this team season was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this team season was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this team season.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user wwho first persisted this team season.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this team season was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this team season was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this team season.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this team season.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }

   /**
    * Returns the roster of players for this team season.
    *
    * @return
    */
   public List<PlayerEntry> getRoster() {
      return roster;
   }

   /**
    * Returns a map of games associated with this team season. Keys represent the date and time of
    * each game.
    *
    * @return
    */
   public Map<LocalDateTime, TeamEvent> getEvents() {
      return events;
   }
}
