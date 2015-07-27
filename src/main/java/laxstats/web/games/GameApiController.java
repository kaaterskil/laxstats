package laxstats.web.games;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import laxstats.api.games.Alignment;
import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.CreateGame;
import laxstats.api.games.DeleteAttendee;
import laxstats.api.games.DeleteGame;
import laxstats.api.games.GameDTO;
import laxstats.api.games.GameId;
import laxstats.api.games.RegisterAttendee;
import laxstats.api.games.UpdateAttendee;
import laxstats.api.games.UpdateGame;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.TeamEvent;
import laxstats.query.players.PlayerEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonNotFoundByDateException;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;
import laxstats.web.teamSeasons.TeamSeasonNotFoundException;
import laxstats.web.teams.TeamNotFoundException;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
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
 * {@code GameApiController} is a RESTful controller providing endpoints for remote clients to
 * access game resources. Security restrictions apply.
 */
@RestController
public class GameApiController extends ApplicationController {

   private final GameQueryRepository eventRepository;
   private final SiteQueryRepository siteRepository;
   private final TeamQueryRepository teamRepository;

   @Autowired
   private GameValidator gameValidator;

   @Autowired
   private AttendeeValidator attendeeValidator;

   /**
    * Creates a {@code GameApiController} with the given arguments.
    *
    * @param userRepository
    * @param commandBus
    * @param eventRepository
    * @param siteRepository
    * @param teamRepository
    */
   @Autowired
   public GameApiController(UserQueryRepository userRepository, CommandBus commandBus,
      GameQueryRepository eventRepository, SiteQueryRepository siteRepository,
      TeamQueryRepository teamRepository) {
      super(userRepository, commandBus);
      this.eventRepository = eventRepository;
      this.siteRepository = siteRepository;
      this.teamRepository = teamRepository;
   }

   @InitBinder(value = "gameResource")
   protected void initGameBinder(WebDataBinder binder) {
      binder.setValidator(gameValidator);
   }

   @InitBinder(value = "attendeeResource")
   protected void initAttendeeBinder(WebDataBinder binder) {
      binder.setValidator(attendeeValidator);
   }

   /*---------- Public action methods ----------*/

   /**
    * GET Returns a collection of all game resources in descending date order.
    *
    * @return
    */
   @RequestMapping(value = "/api/games", method = RequestMethod.GET)
   public List<GameResource> index() {
      final Iterable<GameEntry> games = eventRepository.findAll(gameSortInstance());

      final List<GameResource> list = new ArrayList<>();
      for (final GameEntry each : games) {
         list.add(convertGameEntryToResource(each));
      }
      return list;
   }

   /**
    * GET Returns the game resource matching the given identifier.
    *
    * @param id
    * @return
    */
   @RequestMapping(value = "/api/games/{id}", method = RequestMethod.GET)
   public GameResource show(@PathVariable("id") String id) {
      final GameEntry entity = eventRepository.findOne(id);
      if (entity == null) {
         throw new GameNotFoundException(id);
      }
      return convertGameEntryToResource(entity);
   }

   /*---------- Admin action methods ----------*/

   /**
    * POST
    *
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/games", method = RequestMethod.POST)
   public ResponseEntity<?> create(@Valid @RequestBody GameResourceImpl resource) {
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final GameId identifier = new GameId();

      // Set site
      final String siteId = resource.getSite();
      final SiteEntry site = siteId == null ? null : siteRepository.findOne(siteId);

      // Set team one
      final TeamSeasonEntry teamOneSeason =
         getTeamSeason(resource.getTeamOne(), resource.getStartsAtAsDateTime());
      final Alignment teamOneAlignment =
         getTeamAlignment(teamOneSeason, resource.getAlignment(), resource.isTeamOneHome());

      // Set team two
      final TeamSeasonEntry teamTwoSeason =
         getTeamSeason(resource.getTeamTwo(), resource.getStartsAtAsDateTime());
      final Alignment teamTwoAlignment =
         getTeamAlignment(teamTwoSeason, resource.getAlignment(), resource.isTeamTwoHome());

      final GameDTO dto =
         new GameDTO(identifier.toString(), site, teamOneSeason, teamTwoSeason, teamOneAlignment,
            teamTwoAlignment, resource.getAlignment(), resource.getStartsAtAsDateTime(),
            resource.getSchedule(), resource.getStatus(), resource.getWeather(),
            resource.getDescription(), now, user, now, user);

      final CreateGame payload = new CreateGame(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

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
   @RequestMapping(value = "/api/games/{id}", method = RequestMethod.PUT)
   public ResponseEntity<?> update(@PathVariable("id") String id,
      @Valid @RequestBody GameResourceImpl resource)
   {
      final boolean exists = eventRepository.exists(id);
      if (!exists) {
         throw new GameNotFoundException(id);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final GameId identifier = new GameId(id);

      // Set site
      final String siteId = resource.getSite();
      final SiteEntry site = siteId == null ? null : siteRepository.findOne(siteId);

      // Set team one
      final TeamSeasonEntry teamOneSeason =
         getTeamSeason(resource.getTeamOne(), resource.getStartsAtAsDateTime());
      final Alignment teamOneAlignment =
         getTeamAlignment(teamOneSeason, resource.getAlignment(), resource.isTeamOneHome());

      // Set team two
      final TeamSeasonEntry teamTwoSeason =
         getTeamSeason(resource.getTeamTwo(), resource.getStartsAtAsDateTime());
      final Alignment teamTwoAlignment =
         getTeamAlignment(teamTwoSeason, resource.getAlignment(), resource.isTeamTwoHome());

      final GameDTO dto =
         new GameDTO(identifier.toString(), site, teamOneSeason, teamTwoSeason, teamOneAlignment,
            teamTwoAlignment, resource.getAlignment(), resource.getStartsAtAsDateTime(),
            resource.getSchedule(), resource.getStatus(), resource.getWeather(),
            resource.getDescription(), now, user);

      final UpdateGame payload = new UpdateGame(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param id
    */
   @RequestMapping(value = "/api/games/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   public void delete(@PathVariable("id") String id) {
      final boolean exists = eventRepository.exists(id);
      if (!exists) {
         throw new GameNotFoundException(id);
      }

      checkDeleteGame(id);

      final GameId gameId = new GameId(id);
      final DeleteGame payload = new DeleteGame(gameId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /**
    * POST
    *
    * @param gameId
    * @param resource
    * @return
    */
   @RequestMapping(value = "api/games/{gameId}/teamSeasons/{teamSeasonId}/attendees",
            method = RequestMethod.POST)
   public ResponseEntity<?> createAttendee(@PathVariable("gameId") String gameId,
      @PathVariable("teamSeasonId") String teamSeasonId,
      @Valid @RequestBody AttendeeResourceImpl resource)
   {
      final GameEntry game = eventRepository.findOne(gameId);
      if (game == null) {
         throw new GameNotFoundException(gameId);
      }
      final TeamSeasonEntry teamSeason = game.getTeam(teamSeasonId);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String attendeeId = IdentifierFactory.getInstance()
         .generateIdentifier();
      final PlayerEntry player = teamSeason.getPlayer(resource.getPlayerId());

      final AttendeeDTO dto =
         new AttendeeDTO(attendeeId, game, player, teamSeason, resource.getRole(),
            resource.getStatus(), player.getFullName(), player.getJerseyNumber(), now, user, now,
            user);

      final RegisterAttendee payload = new RegisterAttendee(new GameId(gameId), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      resource.setId(attendeeId);
      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }

   /**
    * PUT
    *
    * @param gameId
    * @param teamSeasonId
    * @param attendeeId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/games/{gameId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}",
            method = RequestMethod.PUT)
   public ResponseEntity<?> updateAttendee(@PathVariable("gameId") String gameId,
      @PathVariable("teamSeasonId") String teamSeasonId,
      @PathVariable("attendeeId") String attendeeId,
      @Valid @RequestBody AttendeeResourceImpl resource)
   {
      final GameEntry game = eventRepository.findOne(gameId);
      if (game == null) {
         throw new GameNotFoundException(gameId);
      }
      final TeamSeasonEntry teamSeason = game.getTeam(teamSeasonId);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }
      final AttendeeEntry attendee = game.getAttendee(attendeeId);
      if (attendee == null) {
         throw new AttendeeNotFoundException(attendeeId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PlayerEntry player = teamSeason.getPlayer(resource.getPlayerId());

      final AttendeeDTO dto =
         new AttendeeDTO(attendeeId, game, player, teamSeason, resource.getRole(),
            resource.getStatus(), player.getFullName(), player.getJerseyNumber(), now, user);

      final UpdateAttendee payload = new UpdateAttendee(new GameId(gameId), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param gameId
    * @param teamSeasonId
    * @param attendeeId
    */
   @RequestMapping(value = "/api/games/{gameId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}",
            method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   public void deleteAttendee(@PathVariable("gameId") String gameId,
      @PathVariable("teamSeasonId") String teamSeasonId,
      @PathVariable("attendeeId") String attendeeId)
   {
      final GameEntry game = eventRepository.findOne(gameId);
      if (game == null) {
         throw new GameNotFoundException(gameId);
      }
      final TeamSeasonEntry teamSeason = game.getTeam(teamSeasonId);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }
      final AttendeeEntry attendee = game.getAttendee(attendeeId);
      if (attendee == null) {
         throw new AttendeeNotFoundException(attendeeId);
      }

      checkDeleteAttendee(gameId, attendeeId);

      final DeleteAttendee payload = new DeleteAttendee(new GameId(gameId), attendeeId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /*---------- Utilities ----------*/

   /**
    * Validates that the given game may be deleted.
    *
    * @param id
    */
   private void checkDeleteGame(String id) {
      // Test if game attendees exist
      final boolean hasAttendees = eventRepository.hasAttendees(id);
      if (hasAttendees) {
         throw new GameHasAttendeesException();
      }

      // Test if plays exist
      final boolean hasPlays = eventRepository.hasPlays(id);
      if (hasPlays) {
         throw new GameHasPlaysException();
      }
   }

   /**
    * Validates that the given game attendee may be deleted.
    *
    * @param gameId
    * @param attendeeId
    */
   private void checkDeleteAttendee(String gameId, String attendeeId) {
      // Test if plays exist
      final boolean hasPlays = eventRepository.hasAttendeePlays(gameId, attendeeId);
      if (hasPlays) {
         throw new AttendeeHasPlaysException();
      }
   }

   /**
    * Returns a game resource from the given game entity, or null if no entity is given.
    *
    * @param entry
    * @return
    */
   private GameResource convertGameEntryToResource(GameEntry entry) {
      if (entry == null) {
         return null;
      }

      final String startsAt = entry.getStartsAt() == null ? null : entry.getStartsAt()
         .toString();
      final String siteId = entry.getSite() == null ? null : entry.getSite()
         .getId();

      String teamOneId = null;
      String teamTwoId = null;
      boolean teamOneHome = false;
      boolean teamTwoHome = false;
      final TeamEvent[] teams = entry.getTeams()
         .toArray(new TeamEvent[2]);
      if (teams.length >= 1 && teams[0] != null) {
         teamOneId = teams[0].getId();
         teamOneHome = teams[0].getAlignment()
            .equals(Alignment.HOME);
      }
      if (teams.length == 2 && teams[1] != null) {
         teamTwoId = teams[1].getId();
         teamTwoHome = teams[0].getAlignment()
            .equals(Alignment.HOME);
      }

      return new GameResourceImpl(entry.getId(), startsAt, entry.getSchedule(), entry.getStatus(),
         siteId, teamOneId, teamTwoId, teamOneHome, teamTwoHome, entry.getAlignment(),
         entry.getDescription(), entry.getConditions());
   }

   /**
    * Returns the team season that includes the given starting date and time, or null if no team is
    * given.
    *
    * @param teamId
    * @param startsAt
    * @return
    */
   private TeamSeasonEntry getTeamSeason(String teamId, LocalDateTime startsAt) {
      if (teamId == null) {
         return null;
      }
      final TeamEntry team = teamRepository.findOne(teamId);
      if (team == null) {
         throw new TeamNotFoundException(teamId);
      }
      final TeamSeasonEntry teamSeason = team.getSeason(startsAt);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundByDateException(startsAt.toString());
      }
      return teamSeason;
   }

   /**
    * Returns the given team's game alignment, or null if no team is given.
    *
    * @param teamSeason
    * @param siteAlignment
    * @param isHomeTeam
    * @return
    */
   private Alignment getTeamAlignment(TeamSeasonEntry teamSeason, SiteAlignment siteAlignment,
      boolean isHomeTeam)
   {
      if (teamSeason != null && siteAlignment != null) {
         if (siteAlignment.equals(SiteAlignment.NEUTRAL)) {
            return Alignment.NEUTRAL;
         }
         return isHomeTeam ? Alignment.HOME : Alignment.AWAY;
      }
      return null;
   }

   /**
    * Returns a comparator that sorts games by starting date in descending order.
    *
    * @return
    */
   private static Sort gameSortInstance() {
      final List<Sort.Order> orders = new ArrayList<>();
      orders.add(new Sort.Order(Sort.Direction.DESC, "startsAt"));
      return new Sort(orders);
   }
}
