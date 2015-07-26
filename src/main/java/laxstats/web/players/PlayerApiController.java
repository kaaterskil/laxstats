package laxstats.web.players;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerId;
import laxstats.api.teamSeasons.DropPlayer;
import laxstats.api.teamSeasons.EditPlayer;
import laxstats.api.teamSeasons.RegisterPlayer;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.players.PlayerEntry;
import laxstats.query.players.PlayerQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;
import laxstats.web.people.PersonNotFoundException;
import laxstats.web.teamSeasons.TeamSeasonNotFoundException;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDate;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code PlayerApiController} is a RESTful controller providing endpoints for remote clients
 * accessing player resources. Security restrictions apply.
 */
@RestController
public class PlayerApiController extends ApplicationController {

   private final PlayerQueryRepository playerRepository;
   private final TeamSeasonQueryRepository teamSeasonRepository;
   private final PersonQueryRepository personRepository;

   @Autowired
   private PlayerValidator playerValidator;

   /**
    * Creates a {@code PlayerApiController} with the given arguments.
    *
    * @param userRepository
    * @param commandBus
    * @param playerRepository
    * @param teamSeasonRepository
    * @param personRepository
    */
   @Autowired
   public PlayerApiController(UserQueryRepository userRepository, CommandBus commandBus,
      PlayerQueryRepository playerRepository, TeamSeasonQueryRepository teamSeasonRepository,
      PersonQueryRepository personRepository) {
      super(userRepository, commandBus);
      this.playerRepository = playerRepository;
      this.teamSeasonRepository = teamSeasonRepository;
      this.personRepository = personRepository;
   }

   @InitBinder
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(playerValidator);
   }

   /*---------- Public action methods ----------*/

   /**
    * GET Returns the collection of players (the roster) for the given team season.
    *
    * @return
    */
   @RequestMapping(value = "/api/teamSeasons/{teamSeasonId}/roster", method = RequestMethod.GET)
   public List<PlayerResource> index(@PathVariable("teamSeasonId") String teamSeasonId) {
      final TeamSeasonEntry teamSeason = teamSeasonRepository.findOne(teamSeasonId);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }

      final List<PlayerResource> list = new ArrayList<>();
      for (final PlayerEntry each : teamSeason.getRoster()) {
         list.add(convertEntryToResource(each));
      }
      return list;
   }

   /**
    * GET Returns a player resource matching the given player identifier and team season identifier.
    *
    * @param id
    * @return
    */
   @RequestMapping(value = "/api/teamSeasons/{teamSeasonId}/roster/{playerId}",
            method = RequestMethod.GET)
   public PlayerResource show(@PathVariable("teamSeasonId") String teamSeasonId,
      @PathVariable("playerId") String playerId)
   {
      final TeamSeasonEntry teamSeason = teamSeasonRepository.findOne(teamSeasonId);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }
      return getPlayerResource(playerId);
   }

   /**
    * GET Returns the player resource matching the given identifier.
    *
    * @param id
    * @return
    */
   @RequestMapping(value = "/api/players/{id}", method = RequestMethod.GET)
   public PlayerResource show(@PathVariable("id") String id) {
      return getPlayerResource(id);
   }

   /*---------- Admin action methods ----------*/

   /**
    * POST
    *
    * @param teamSeasonId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/teamSeasons/{teamSeasonId}/roster", method = RequestMethod.POST)
   public ResponseEntity<?> create(@PathVariable("teamSeasonId") String teamSeasonId,
      @Valid @RequestBody PlayerResourceImpl resource)
   {
      final TeamSeasonEntry teamSeason = teamSeasonRepository.findOne(teamSeasonId);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }

      final PersonEntry person = personRepository.findOne(resource.getPerson());
      if (person == null) {
         throw new PersonNotFoundException(resource.getPerson());
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PlayerId identifier = new PlayerId();

      final PlayerDTO dto =
         new PlayerDTO(identifier, person, teamSeason, person.getFullName(), resource.getRole(),
            resource.getStatus(), resource.getJerseyNumber(), resource.getPosition(),
            resource.isCaptain(), resource.getDepth(), resource.getHeight(), resource.getWeight(),
            resource.isReleased(), resource.getParentReleaseSentOnAsLocalDate(),
            resource.getParentReleaseReceivedOnAsLocalDate(), now, user, now, user);

      final RegisterPlayer payload = new RegisterPlayer(new TeamSeasonId(teamSeasonId), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      resource.setId(identifier.toString());
      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }

   /**
    * PUT
    *
    * @param teamSeasonId
    * @param playerId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/teamSeasons/{teamSeasonId}/roster/{playerId}",
            method = RequestMethod.PUT)
   public ResponseEntity<?> update(@PathVariable("teamSeasonId") String teamSeasonId,
      @PathVariable("playerId") String playerId, @Valid @RequestBody PlayerResourceImpl resource)
   {
      final TeamSeasonEntry teamSeason = teamSeasonRepository.findOne(teamSeasonId);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }

      final PersonEntry person = personRepository.findOne(resource.getPerson());
      if (person == null) {
         throw new PersonNotFoundException(resource.getPerson());
      }

      final boolean exists = playerRepository.exists(playerId);
      if (!exists) {
         throw new PlayerNotFoundException(playerId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PlayerId identifier = new PlayerId(playerId);

      final PlayerDTO dto =
         new PlayerDTO(identifier, person, teamSeason, person.getFullName(), resource.getRole(),
            resource.getStatus(), resource.getJerseyNumber(), resource.getPosition(),
            resource.isCaptain(), resource.getDepth(), resource.getHeight(), resource.getWeight(),
            resource.isReleased(), resource.getParentReleaseSentOnAsLocalDate(),
            resource.getParentReleaseReceivedOnAsLocalDate(), now, user);

      final EditPlayer payload = new EditPlayer(new TeamSeasonId(teamSeasonId), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param teamSeasonId
    * @param playerId
    */
   @RequestMapping(value = "/api/teamSeasons/{teamSeasonId}/roster/{playerId}",
            method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   public void delete(@PathVariable("teamSeasonId") String teamSeasonId,
      @PathVariable("playerId") String playerId)
   {
      final TeamSeasonEntry teamSeason = teamSeasonRepository.findOne(teamSeasonId);
      if (teamSeason == null) {
         throw new TeamSeasonNotFoundException(teamSeasonId);
      }

      final boolean exists = playerRepository.exists(playerId);
      if (!exists) {
         throw new PlayerNotFoundException(playerId);
      }

      checkDelete(playerId);

      final DropPlayer payload =
         new DropPlayer(new TeamSeasonId(teamSeasonId), new PlayerId(playerId));
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /*---------- Utilities ----------*/

   private void checkDelete(String id) {
      // Test if the player attended games
      final boolean playedGames = playerRepository.hasAttendedGames(id);
      if (playedGames) {
         throw new PlayerAttendedGamesException();
      }

      // Test if the player has associated plays
      final boolean hasPlays = playerRepository.hasPlays(id);
      if (hasPlays) {
         throw new PlayerHasPlaysException();
      }
   }

   private PlayerResource getPlayerResource(String id) {
      final PlayerEntry entity = playerRepository.findOne(id);
      if (entity == null) {
         throw new PlayerNotFoundException(id);
      }
      return convertEntryToResource(entity);
   }

   private PlayerResource convertEntryToResource(PlayerEntry entity) {
      final String releaseSentOn = convertLocalDate(entity.getParentReleaseSentOn());
      final String releaseReceivedOn = convertLocalDate(entity.getParentReleaseSentOn());

      return new PlayerResourceImpl(entity.getId(), entity.getPerson()
         .getId(), entity.getTeamSeason()
         .getId(), entity.getFullName(), entity.getRole(), entity.getStatus(),
         entity.getJerseyNumber(), entity.getPosition(), entity.isCaptain(), entity.getDepth(),
         entity.getHeight(), entity.getWeight(), entity.isParentReleased(), releaseSentOn,
         releaseReceivedOn);

   }

   private String convertLocalDate(LocalDate date) {
      return date == null ? null : date.toString();
   }
}
