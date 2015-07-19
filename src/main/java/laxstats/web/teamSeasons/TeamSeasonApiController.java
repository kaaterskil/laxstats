package laxstats.web.teamSeasons;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import laxstats.api.teamSeasons.DeleteTeamSeason;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teams.EditTeamSeason;
import laxstats.api.teams.RegisterTeamSeason;
import laxstats.api.teams.TeamId;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.leagues.LeagueQueryRepository;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;
import laxstats.web.teams.InvalidTeamOwnerException;
import laxstats.web.teams.TeamNotFoundException;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code TeamSeasonApiController} is a RESTful controller providing API endpoints for remote
 * clients accessing team season resources. Security restrictions apply.
 */
@RestController
@RequestMapping(value = "/api/teams/{teamId}")
public class TeamSeasonApiController extends ApplicationController {

   private final TeamQueryRepository teamRepository;
   private final TeamSeasonQueryRepository teamSeasonRepository;
   private final SeasonQueryRepository seasonRepository;
   private final LeagueQueryRepository leagueRepository;

   @Autowired
   private TeamSeasonValidator teamSeasonValidator;

   /**
    * Creates a {@code TeamSeasonApiController} with the given arguments.
    *
    * @param teamRepository
    * @param seasonRepository
    * @param userRepository
    * @param commandBus
    */
   @Autowired
   public TeamSeasonApiController(TeamQueryRepository teamRepository,
      TeamSeasonQueryRepository teamSeasonRepository, SeasonQueryRepository seasonRepository,
      LeagueQueryRepository leagueRepository, UserQueryRepository userRepository,
      CommandBus commandBus) {
      super(userRepository, commandBus);
      this.teamRepository = teamRepository;
      this.teamSeasonRepository = teamSeasonRepository;
      this.seasonRepository = seasonRepository;
      this.leagueRepository = leagueRepository;
   }

   @InitBinder
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(teamSeasonValidator);
   }

   /*---------- Public action methods ----------*/

   /**
    * GET Returns a collection of team seasons, sorted in descending date order, for the team
    * matching the given identifier.
    *
    * @param teamId
    * @return
    */
   @RequestMapping(value = "/seasons", method = RequestMethod.GET)
   public List<TeamSeasonResource> index(@PathVariable("teamId") String teamId) {
      final TeamEntry team = teamRepository.findOne(teamId);
      if (team == null) {
         throw new TeamNotFoundException(teamId);
      }

      final List<TeamSeasonResource> list = new ArrayList<>();
      for (final TeamSeasonEntry each : team.getSeasons()) {
         final String leagueId = each.getLeague() == null ? null : each.getLeague()
            .getId();
         final String startsOn = each.getStartsOn() == null ? null : each.getStartsOn()
            .toString();
         final String endsOn = each.getEndsOn() == null ? null : each.getEndsOn()
            .toString();

         final TeamSeasonResource resource = new TeamSeasonResourceImpl(each.getId(), each.getTeam()
            .getId(), each.getSeason()
            .getId(), leagueId, startsOn, endsOn, each.getName(), each.getStatus(), each.getTeam()
            .getTitle());
         list.add(resource);
      }
      return list;
   }

   /**
    * GET Returns the team season resource with the given identifier.
    *
    * @param teamSeasonId
    * @return
    */
   @RequestMapping(value = "/seasons/{teamSeasonId}", method = RequestMethod.GET)
   public TeamSeasonResource show(@PathVariable("teamSeasonId") String teamSeasonId) {
      final TeamSeasonEntry entity = teamSeasonRepository.findOne(teamSeasonId);
      if (entity == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }

      final String leagueId = entity.getLeague() == null ? null : entity.getLeague()
         .getId();
      final String startsOn = entity.getStartsOn() == null ? null : entity.getStartsOn()
         .toString();
      final String endsOn = entity.getEndsOn() == null ? null : entity.getEndsOn()
         .toString();

      return new TeamSeasonResourceImpl(entity.getId(), entity.getTeam()
         .getId(), entity.getSeason()
         .getId(), leagueId, startsOn, endsOn, entity.getName(), entity.getStatus(),
         entity.getTeam()
            .getTitle());
   }

   /*---------- Admin action methods ----------*/

   /**
    * POST
    *
    * @param resource
    * @return
    */
   @RequestMapping(value = "/seasons", method = RequestMethod.POST)
   public ResponseEntity<?> create(@Valid @RequestBody TeamSeasonResourceImpl resource) {
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final TeamEntry team = teamRepository.findOne(resource.getTeam());
      final SeasonEntry season = seasonRepository.findOne(resource.getSeason());
      final TeamSeasonId identifier = new TeamSeasonId();

      LeagueEntry league = null;
      if (resource.getLeague() != null) {
         league = leagueRepository.findOne(resource.getLeague());
      }

      final TeamSeasonDTO dto =
         new TeamSeasonDTO(identifier, team, season, league, resource.getStartsOnAsLocalDate(),
            resource.getEndsOnAsLocalDate(), resource.getName(), resource.getStatus(), user, now,
            user, now);

      final RegisterTeamSeason payload = new RegisterTeamSeason(new TeamId(resource.getTeam()), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      resource.setId(identifier.toString());
      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }

   /**
    * PUT
    *
    * @param teamSeasonId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/seasons/{teamSeasonId}", method = RequestMethod.PUT)
   public ResponseEntity<?> update(@PathVariable("teamSeasonId") String teamSeasonId,
      @Valid @RequestBody TeamSeasonResourceImpl resource)
   {
      final boolean exists = teamSeasonRepository.exists(teamSeasonId);
      if (!exists) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final TeamEntry team = teamRepository.findOne(resource.getTeam());
      final SeasonEntry season = seasonRepository.findOne(resource.getSeason());
      final TeamSeasonId identifier = new TeamSeasonId(teamSeasonId);

      LeagueEntry league = null;
      if (resource.getLeague() != null) {
         league = leagueRepository.findOne(resource.getLeague());
      }

      final TeamSeasonDTO dto =
         new TeamSeasonDTO(identifier, team, season, league, resource.getStartsOnAsLocalDate(),
            resource.getEndsOnAsLocalDate(), resource.getName(), resource.getStatus(), user, now);

      final EditTeamSeason payload = new EditTeamSeason(new TeamId(resource.getTeam()), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param teamSeasonId
    */
   @RequestMapping(value = "/seasons/{teamSeasonId}", method = RequestMethod.DELETE)
   public void delete(@PathVariable("teamSeasonId") String teamSeasonId) {
      final TeamSeasonEntry entity = teamSeasonRepository.findOne(teamSeasonId);
      if (entity == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }

      checkDelete(entity);

      final DeleteTeamSeason command = new DeleteTeamSeason(new TeamSeasonId(teamSeasonId));
      commandBus.dispatch(new GenericCommandMessage<>(command));
   }

   /*---------- Utilities ----------*/

   private void checkDelete(TeamSeasonEntry entity) {
      // Validate that the current user is associated with the team.
      final UserEntry user = getCurrentUser();
      final TeamEntry team = entity.getTeam();
      if (user.getTeam() != null && !user.getTeam()
         .equals(team)) {
         throw new InvalidTeamOwnerException();
      }

      // Validate that the team has no associated games.
      if (!entity.getEvents()
         .isEmpty()) {
         throw new DeleteTeamSeasonWithHistoryException();
      }
   }
}
