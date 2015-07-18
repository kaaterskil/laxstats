package laxstats.web.teams;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import laxstats.api.teams.CreateTeam;
import laxstats.api.teams.DeleteTeam;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.UpdateTeam;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.leagues.LeagueQueryRepository;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code TeamApiController} is a RESTful controller for remote clients accessing team resources.
 * Security restrictions apply.
 */
@RestController
public class TeamApiController extends ApplicationController {

   private final TeamQueryRepository teamRepository;
   private final SiteQueryRepository siteRepository;
   private final LeagueQueryRepository leagueRepository;
   private final Sort teamSort = new Sort("region", "sponsor");

   @Autowired
   private TeamValidator teamValidator;

   /**
    * Creates a {@code TeamApiController} with the given arguments.
    *
    * @param userRepository
    * @param commandBus
    * @param teamRepository
    * @param siteRepository
    * @param leagueRepository
    */
   @Autowired
   public TeamApiController(UserQueryRepository userRepository, CommandBus commandBus,
      TeamQueryRepository teamRepository, SiteQueryRepository siteRepository,
      LeagueQueryRepository leagueRepository) {
      super(userRepository, commandBus);
      this.teamRepository = teamRepository;
      this.siteRepository = siteRepository;
      this.leagueRepository = leagueRepository;
   }

   @InitBinder
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(teamValidator);
   }

   /*---------- Public action methods ----------*/

   /**
    * GET Returns a collection of teams ordered by region and sponsor.
    *
    * @return
    */
   @RequestMapping(value = "/api/teams", method = RequestMethod.GET)
   public List<TeamResource> index() {
      final Iterable<TeamEntry> teams = teamRepository.findAll(teamSort);

      final List<TeamResource> list = new ArrayList<>();
      for (final TeamEntry each : teams) {
         final String siteId = each.getHomeSite() == null ? null : each.getHomeSite()
            .getId();
         final String leagueId = each.getLeague() == null ? null : each.getLeague()
            .getId();

         final TeamResource resource =
            new TeamResourceImpl(each.getId(), each.getSponsor(), each.getName(),
               each.getAbbreviation(), each.getGender(), each.getLetter(), each.getRegion(), siteId,
               leagueId);
         list.add(resource);
      }
      return list;
   }

   /**
    * GET Returns the team resource matching the given identifier.
    *
    * @param id
    * @return
    */
   @RequestMapping(value = "/api/teams/{id}", method = RequestMethod.GET)
   public TeamResource show(@PathVariable String id) {
      final TeamEntry entity = teamRepository.findOne(id);
      if (entity == null) {
         throw new TeamNotFoundException(id);
      }

      final String siteId = entity.getHomeSite() == null ? null : entity.getHomeSite()
         .getId();
      final String leagueId = entity.getLeague() == null ? null : entity.getLeague()
         .getId();

      return new TeamResourceImpl(entity.getId(), entity.getSponsor(), entity.getName(),
         entity.getAbbreviation(), entity.getGender(), entity.getLetter(), entity.getRegion(),
         siteId, leagueId);
   }

   /*---------- Admin action methods ----------*/

   /**
    * POST
    *
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/teams", method = RequestMethod.POST)
   public ResponseEntity<?> create(@Valid @RequestBody TeamResourceImpl resource) {
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final TeamId identifier = new TeamId();

      final String siteId = resource.getHomeSite();
      final SiteEntry homeSite = siteId == null ? null : siteRepository.findOne(siteId);

      final String leagueId = resource.getLeague();
      final LeagueEntry league = leagueId == null ? null : leagueRepository.findOne(leagueId);

      final TeamDTO dto =
         new TeamDTO(identifier, resource.getSponsor(), resource.getName(),
            resource.getAbbreviation(), resource.getGender(), resource.getLetter(),
            resource.getRegion(), league, homeSite, now, user, now, user);

      final CreateTeam command = new CreateTeam(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(command));

      resource.setId(identifier.toString());
      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }

   /**
    * PUT
    *
    * @param id
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/teams/{id}", method = RequestMethod.PUT)
   public ResponseEntity<?> update(@PathVariable String id,
      @Valid @RequestBody TeamResourceImpl resource)
   {
      final boolean exists = teamRepository.exists(id);
      if (!exists) {
         throw new TeamNotFoundException(id);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final TeamId identifier = new TeamId(id);

      final String siteId = resource.getHomeSite();
      final SiteEntry homeSite = siteId == null ? null : siteRepository.findOne(siteId);

      final String leagueId = resource.getLeague();
      final LeagueEntry league = leagueId == null ? null : leagueRepository.findOne(leagueId);

      final TeamDTO dto =
         new TeamDTO(identifier, resource.getSponsor(), resource.getName(),
            resource.getAbbreviation(), resource.getGender(), resource.getLetter(),
            resource.getRegion(), league, homeSite, now, user);

      final UpdateTeam command = new UpdateTeam(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(command));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param id
    */
   @RequestMapping(value = "/api/teams/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   public void delete(@PathVariable String id) {
      final TeamEntry entity = teamRepository.findOne(id);
      if (entity == null) {
         throw new TeamNotFoundException(id);
      }

      checkDelete(entity);

      final DeleteTeam command = new DeleteTeam(new TeamId(id));
      commandBus.dispatch(new GenericCommandMessage<>(command));
   }

   /*---------- Utilities ----------*/

   private void checkDelete(TeamEntry team) {
      // Test if the current user is associated with the team.
      final UserEntry user = getCurrentUser();
      if (user.getTeam() != null && !user.getTeam()
         .equals(team)) {
         throw new InvalidTeamOwnerException();
      }

      // Test if the team is associated with any games.
      for (final TeamSeasonEntry each : team.getSeasons()) {
         if (!each.getEvents()
            .isEmpty()) {
            throw new DeleteTeamWithHistoryException();
         }
      }
   }
}
