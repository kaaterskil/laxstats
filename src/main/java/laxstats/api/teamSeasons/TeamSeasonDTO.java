package laxstats.api.teamSeasons;

import java.io.Serializable;

import laxstats.query.leagues.LeagueEntry;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * {@code TeamSeasonDTO} transfers team season information between the application and data layers.
 */
public class TeamSeasonDTO implements Serializable {
   private static final long serialVersionUID = 313481608473386156L;

   private final TeamSeasonId teamSeasonId;
   private final TeamEntry team;
   private final SeasonEntry season;
   private final LeagueEntry league;
   private final LocalDate startsOn;
   private final LocalDate endsOn;
   private final String name;
   private final TeamStatus status;
   private final UserEntry createdBy;
   private final LocalDateTime createdAt;
   private final UserEntry modifiedBy;
   private final LocalDateTime modifiedAt;

   /**
    * Creates a {@code TeamSeasonDTO} with the given information.
    * 
    * @param teamSeasonId
    * @param team
    * @param season
    * @param league
    * @param startsOn
    * @param endsOn
    * @param name
    * @param status
    * @param createdBy
    * @param createdAt
    * @param modifiedBy
    * @param modifiedAt
    */
   public TeamSeasonDTO(TeamSeasonId teamSeasonId, TeamEntry team, SeasonEntry season,
      LeagueEntry league, LocalDate startsOn, LocalDate endsOn, String name, TeamStatus status,
      UserEntry createdBy, LocalDateTime createdAt, UserEntry modifiedBy, LocalDateTime modifiedAt) {
      this.teamSeasonId = teamSeasonId;
      this.team = team;
      this.season = season;
      this.league = league;
      this.startsOn = startsOn;
      this.endsOn = endsOn;
      this.name = name;
      this.status = status;
      this.createdBy = createdBy;
      this.createdAt = createdAt;
      this.modifiedBy = modifiedBy;
      this.modifiedAt = modifiedAt;
   }

   /**
    * Creates a {@code TeamSeasonDTO} with the given information.
    * 
    * @param teamSeasonId
    * @param team
    * @param season
    * @param league
    * @param startsOn
    * @param endsOn
    * @param name
    * @param status
    * @param modifiedBy
    * @param modifiedAt
    */
   public TeamSeasonDTO(TeamSeasonId teamSeasonId, TeamEntry team, SeasonEntry season,
      LeagueEntry league, LocalDate startsOn, LocalDate endsOn, String name, TeamStatus status,
      UserEntry modifiedBy, LocalDateTime modifiedAt) {
      this(teamSeasonId, team, season, league, startsOn, endsOn, name, status, null, null,
         modifiedBy, modifiedAt);
   }

   /**
    * Returns the aggregate identifier of the team season.
    * 
    * @return
    */
   public TeamSeasonId getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Returns the parent team.
    * 
    * @return
    */
   public TeamEntry getTeam() {
      return team;
   }

   /**
    * Returns the associated {@code Season}
    * 
    * @return
    */
   public SeasonEntry getSeason() {
      return season;
   }

   /**
    * Returns the associated {@code League}.
    * 
    * @return
    */
   public LeagueEntry getLeague() {
      return league;
   }

   /**
    * Returns the season start date.
    * 
    * @return
    */
   public LocalDate getStartsOn() {
      return startsOn;
   }

   /**
    * Returns the season ending date.
    * 
    * @return
    */
   public LocalDate getEndsOn() {
      return endsOn;
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
    * Returns the team season status.
    * 
    * @return
    */
   public TeamStatus getStatus() {
      return status;
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
    * Returns the date and time this team season first persisted.
    * 
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
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
    * Returns the date and time this team season was last modified.
    * 
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }
}
