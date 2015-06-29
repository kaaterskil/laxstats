package laxstats.domain.teams;

import java.util.ArrayList;
import java.util.List;

import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teams.TeamCreated;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamDeleted;
import laxstats.api.teams.TeamGender;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.TeamPasswordCreated;
import laxstats.api.teams.TeamPasswordUpdated;
import laxstats.api.teams.TeamSeasonEdited;
import laxstats.api.teams.TeamSeasonRegistered;
import laxstats.api.teams.TeamUpdated;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * {@code Team} represents a domain object model of a team aggregate. Teams span an unlimited number
 * of playing seasons.
 */
public class Team extends AbstractAnnotatedAggregateRoot<TeamId> {
   private static final long serialVersionUID = 88398210263680003L;

   @AggregateIdentifier
   private TeamId teamId;

   private String name;
   private TeamGender gender;
   private String homeSiteId;
   private String encodedPassword;
   private final List<TeamSeasonInfo> seasons = new ArrayList<>();

   /**
    * Applies a creation event to the team aggregate with the given identifier and data.
    *
    * @param teamId
    * @param teamDTO
    */
   public Team(TeamId teamId, TeamDTO teamDTO) {
      apply(new TeamCreated(teamId, teamDTO));
   }

   /**
    * Creates a new team. Internal use only.
    */
   protected Team() {
   }

   /**
    * Instructs the framework to update and persist the team with the given data.
    *
    * @param teamId
    * @param teamDTO
    */
   public void update(TeamId teamId, TeamDTO teamDTO) {
      apply(new TeamUpdated(teamId, teamDTO));
   }

   /**
    * Instructs the framework to delete the team.
    *
    * @param teamId
    */
   public void delete(TeamId teamId) {
      apply(new TeamDeleted(teamId));
   }

   /**
    * Instructs the framework to create a new password.
    *
    * @param teamId
    * @param teamDTO
    */
   public void createPassword(TeamId teamId, TeamDTO teamDTO) {
      apply(new TeamPasswordCreated(teamId, teamDTO));
   }

   /**
    * Instructs the framework to update the team password.
    *
    * @param teamId
    * @param teamDTO
    */
   public void updatePassword(TeamId teamId, TeamDTO teamDTO) {
      apply(new TeamPasswordUpdated(teamId, teamDTO));
   }

   /**
    * Instructs the framework to register a new season.
    *
    * @param dto
    */
   public void registerSeason(TeamSeasonDTO dto) {
      apply(new TeamSeasonRegistered(teamId, dto));
   }

   /**
    * Returns true if a season with the given unique identifier can be registered, false otherwise.
    *
    * @param seasonId
    * @return
    */
   public boolean canRegisterSeason(String seasonId) {
      for (final TeamSeasonInfo each : seasons) {
         if (each.getSeasonId().equals(seasonId)) {
            return false;
         }
      }
      return true;
   }

   /**
    * Instructs the framework to update a team season with the given data.
    *
    * @param dto
    */
   public void updateSeason(TeamSeasonDTO dto) {
      apply(new TeamSeasonEdited(teamId, dto));
   }

   /**
    * Stores and persists new team data based on information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamCreated event) {
      final TeamDTO dto = event.getTeamDTO();
      teamId = event.getTeamId();
      name = dto.getName();
      gender = dto.getGender();
      if (dto.getHomeSite() != null) {
         homeSiteId = dto.getHomeSite().toString();
      }
   }

   /**
    * Updates and persists the team with information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamUpdated event) {
      final TeamDTO dto = event.getTeamDTO();
      name = dto.getName();
      gender = dto.getGender();
      if (dto.getHomeSite() != null) {
         homeSiteId = dto.getHomeSite().toString();
      }
   }

   /**
    * Marks the team for deletion.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamDeleted event) {
      markDeleted();
   }

   /**
    * Registers a new team season value object from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamSeasonRegistered event) {
      final TeamSeasonDTO dto = event.getTeamSeasonDTO();

      final TeamSeasonInfo vo =
         new TeamSeasonInfo(dto.getTeamSeasonId().toString(), dto.getTeam().getId(), dto.getSeason()
            .getId(), dto.getStartsOn(), dto.getEndsOn(), dto.getStatus());
      seasons.add(vo);
   }

   /**
    * Replaces a team season with a new value object built from information contained in the given
    * event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamSeasonEdited event) {
      final TeamSeasonDTO dto = event.getTeamSeasonDTO();
      final String id = dto.getTeamSeasonId().toString();

      for (final TeamSeasonInfo each : seasons) {
         if (each.getId().equals(id)) {
            seasons.remove(each);
         }
      }
      final TeamSeasonInfo vo =
         new TeamSeasonInfo(dto.getTeamSeasonId().toString(), dto.getTeam().getId(), dto.getSeason()
            .getId(), dto.getStartsOn(), dto.getEndsOn(), dto.getStatus());
      seasons.add(vo);
   }

   /**
    * Creates and persists a new password from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamPasswordCreated event) {
      final TeamDTO dto = event.getTeamDTO();
      encodedPassword = dto.getEncodedPassword();
   }

   /**
    * Updates and persists the team password from information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(TeamPasswordUpdated event) {
      final TeamDTO dto = event.getTeamDTO();
      encodedPassword = dto.getEncodedPassword();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public TeamId getIdentifier() {
      return teamId;
   }

   /**
    * Returns the team name.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the team gender.
    *
    * @return
    */
   public TeamGender getGender() {
      return gender;
   }

   /**
    * Returns the identifier of the home site.
    *
    * @return
    */
   public String getHomeSiteId() {
      return homeSiteId;
   }

   /**
    * Returns the encoded password.
    *
    * @return
    */
   public String getEncodedPassword() {
      return encodedPassword;
   }

   /**
    * Returns the collection of team seasons.
    *
    * @return
    */
   public List<TeamSeasonInfo> getSeasons() {
      return seasons;
   }

}
