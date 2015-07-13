package laxstats.web.seasons;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.seasons.CreateSeason;
import laxstats.api.seasons.DeleteSeason;
import laxstats.api.seasons.SeasonDTO;
import laxstats.api.seasons.SeasonId;
import laxstats.api.seasons.UpdateSeason;
import laxstats.api.utils.Common;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;
import laxstats.web.ExceptionResource;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code SeasonApiController} is a RESTful controller for remote clients accessing season
 * resources. Security restrictions apply.
 */
@RestController
public class SeasonApiController extends ApplicationController {

   private final SeasonQueryRepository seasonRepository;
   private final Sort seasonSort = new Sort(Direction.DESC, "startsOn");

   @Autowired
   private SeasonValidator seasonValidator;

   /**
    * Creates a {@code SeasonApiController} with the given arguments.
    *
    * @param seasonRepository
    * @param userRepository
    * @param commandBus
    */
   @Autowired
   public SeasonApiController(SeasonQueryRepository seasonRepository,
      UserQueryRepository userRepository, CommandBus commandBus) {
      super(userRepository, commandBus);
      this.seasonRepository = seasonRepository;
   }

   @InitBinder
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(seasonValidator);
   }

   /*---------- Public action methods ----------*/

   /**
    * GET Returns a collection of seasons sorted by starting date in descending order.
    *
    * @return
    */
   @RequestMapping(value = "/api/seasons", method = RequestMethod.GET)
   public List<SeasonResource> index() {
      final Iterable<SeasonEntry> seasons = seasonRepository.findAll(seasonSort);

      final List<SeasonResource> list = new ArrayList<>();
      for (final SeasonEntry each : seasons) {
         final SeasonResourceImpl resource =
            new SeasonResourceImpl(each.getId(), each.getDescription(), each.getStartsOn()
               .toString(), each.getEndsOn()
               .toString());
         list.add(resource);
      }
      return list;
   }

   /**
    * GET Returns the season matching the given aggregate identifier.
    *
    * @param seasonId
    * @return
    */
   @RequestMapping(value = "/api/seasons/{seasonId}", method = RequestMethod.GET)
   public SeasonResource show(@PathVariable String seasonId) {
      final SeasonEntry season = seasonRepository.findOne(seasonId);
      if (season == null) {
         throw new SeasonNotFoundException(seasonId);
      }

      return new SeasonResourceImpl(season.getId(), season.getDescription(), season.getStartsOn()
         .toString(), season.getEndsOn()
         .toString());
   }

   /*---------- Admin action methods ----------*/

   /**
    * POST
    *
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/seasons", method = RequestMethod.POST)
   public ResponseEntity<?> create(@Valid @RequestBody SeasonResourceImpl resource) {
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SeasonId identifier = new SeasonId();

      // Truncate the date values to YYYY-MM-DD strings
      final LocalDate startsOn = resource.getStartsOnAsLocalDate();
      final LocalDate endsOn = resource.getEndsOnAsLocalDate();

      final SeasonDTO dto =
         new SeasonDTO(identifier, resource.getDescription(), startsOn, endsOn, now, user, now, user);

      final CreateSeason payload = new CreateSeason(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      resource.setId(identifier.toString());
      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }


   /**
    * PUT
    *
    * @param seasonId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/seasons/{seasonId}", method = RequestMethod.PUT)
   public ResponseEntity<?> update(@PathVariable String seasonId,
      @Valid @RequestBody SeasonResourceImpl resource)
   {
      final boolean exists = seasonRepository.exists(seasonId);
      if (!exists) {
         throw new SeasonNotFoundException(seasonId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SeasonId identifier = new SeasonId(seasonId);

      // Truncate the date values to YYYY-MM-DD strings
      final LocalDate startsOn =
         resource.getStartsOn() != null ? LocalDate.parse(resource.getStartsOn()
            .substring(0, 10)) : null;
      final LocalDate endsOn = resource.getEndsOn() != null ? LocalDate.parse(resource.getEndsOn()
         .substring(0, 10)) : null;

      final SeasonDTO dto =
         new SeasonDTO(identifier, resource.getDescription(), startsOn, endsOn, now, user);

      final UpdateSeason payload = new UpdateSeason(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param seasonId
    */
   @RequestMapping(value = "/api/seasons/{seasonId}", method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   public void delete(@PathVariable String seasonId) {
      final boolean exists = seasonRepository.exists(seasonId);
      if (!exists) {
         throw new SeasonNotFoundException(seasonId);
      }

      final SeasonId identifier = new SeasonId(seasonId);
      checkDelete(seasonId);
      final DeleteSeason payload = new DeleteSeason(identifier);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /*---------- Utilities ----------*/

   private void checkDelete(String seasonId) {
      final int foundTeams = seasonRepository.countTeamSeasons(seasonId);
      if (foundTeams > 0) {
         throw new IllegalArgumentException("Cannot delete season with associated teams.");
      }

      final SeasonEntry season = seasonRepository.findOne(seasonId);
      final LocalDateTime startsAt = season.getStartsOn()
         .toDateTimeAtStartOfDay()
         .toLocalDateTime();
      final LocalDateTime endsAt = season.getEndsOn() == null ? Common.EOT : season.getEndsOn()
         .toDateTimeAtStartOfDay()
         .toLocalDateTime();
      final int foundGames = seasonRepository.countGames(startsAt, endsAt);
      if (foundGames > 0) {
         throw new IllegalArgumentException("Cannot delete season with associated games.");
      }
   }

   @ResponseStatus(value = HttpStatus.BAD_REQUEST)
   @ExceptionHandler(value = IllegalArgumentException.class)
   public ExceptionResource handleExceptions(HttpServletRequest req, IllegalArgumentException ex) {
      final String url = req.getRequestURL()
         .toString();
      return new ExceptionResource(url, ex);
   }
}
