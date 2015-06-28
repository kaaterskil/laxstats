package laxstats.web.seasons;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

/**
 * Controller for API calls
 */
@RestController
@RequestMapping(value = "/admin/api/seasons")
public class SeasonApiController extends ApplicationController {
   private final SeasonQueryRepository seasonRepository;

   @Autowired
   private SeasonFormValidator seasonValidator;

   @Autowired
   public SeasonApiController(SeasonQueryRepository seasonRepository,
      UserQueryRepository userRepository, CommandBus commandBus) {
      super(userRepository, commandBus);
      this.seasonRepository = seasonRepository;
   }

   @InitBinder(value = "SeasonInfo")
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(seasonValidator);
   }

   /*---------- Action methods ----------*/

   /**
    * GET Returns a collection of {@code SeasonInfo} objects sorted by starting date in descending
    * order.
    *
    * @return
    */
   @RequestMapping(method = RequestMethod.GET)
   public List<SeasonInfo> seasonIndex() {
      final Iterable<SeasonEntry> seasons =
         seasonRepository.findAll(new Sort(Direction.DESC, "startsOn"));

      final List<SeasonInfo> list = new ArrayList<SeasonInfo>();
      for (final SeasonEntry each : seasons) {
         final SeasonInfo resource = new SeasonInfo(each.getId(), each.getDescription(),
            each.getStartsOn().toString(), each.getEndsOn().toString());
         list.add(resource);
      }
      return list;
   }

   /**
    * GET Returns a {@code SeasonInfo} representing a {@code SeasonEntry} corresponding to the given
    * primary key.
    *
    * @param season
    * @return
    */
   @RequestMapping(value = "/{seasonId}", method = RequestMethod.GET)
   public SeasonInfo showSeason(@PathVariable("seasonId") SeasonEntry season) {
      return new SeasonInfo(season.getId(), season.getDescription(), season.getStartsOn().toString(),
         season.getEndsOn().toString());
   }

   /**
    * POST
    *
    * @param resource
    * @param bindingResult
    * @return
    */
   @RequestMapping(method = RequestMethod.POST)
   public SeasonInfo createSeason(@Valid @RequestBody SeasonInfo resource,
      BindingResult bindingResult)
   {
      if (bindingResult.hasErrors()) {
         return resource;
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SeasonId identifier = new SeasonId();

      // Truncate the date values to YYY-MM-DD strings
      final LocalDate startsOn = resource.getStartsOn() != null
         ? LocalDate.parse(resource.getStartsOn().substring(0, 10)) : null;
      final LocalDate endsOn = resource.getEndsOn() != null
         ? LocalDate.parse(resource.getEndsOn().substring(0, 10)) : null;

      final SeasonDTO dto = new SeasonDTO(identifier, resource.getDescription(), startsOn, endsOn,
         now, user, now, user);

      final CreateSeason payload = new CreateSeason(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      resource.setId(identifier.toString());
      return resource;
   }


   /**
    * PUT
    *
    * @param seasonId
    * @param resource
    * @param bindingResult
    * @return
    */
   @RequestMapping(value = "/{seasonId}", method = RequestMethod.PUT)
   public SeasonInfo updateSeason(@PathVariable String seasonId,
      @Valid @RequestBody SeasonInfo resource, BindingResult bindingResult)
   {
      if (bindingResult.hasErrors()) {
         return resource;
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SeasonId identifier = new SeasonId(seasonId);

      // Truncate the date values to YYYY-MM-DD strings
      final LocalDate startsOn = resource.getStartsOn() != null
         ? LocalDate.parse(resource.getStartsOn().substring(0, 10)) : null;
      final LocalDate endsOn = resource.getEndsOn() != null
         ? LocalDate.parse(resource.getEndsOn().substring(0, 10)) : null;

      final SeasonDTO dto =
         new SeasonDTO(identifier, resource.getDescription(), startsOn, endsOn, now, user);

      final UpdateSeason payload = new UpdateSeason(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return resource;
   }

   /**
    * DELETE
    *
    * @param seasonId
    */
   @RequestMapping(value = "/{seasonId}", method = RequestMethod.DELETE)
   public void deleteSeason(@PathVariable String seasonId) {
      final SeasonId identifier = new SeasonId(seasonId);
      checkDelete(seasonId);
      final DeleteSeason payload = new DeleteSeason(identifier);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   private void checkDelete(String seasonId) {
      final int foundTeams = seasonRepository.countTeamSeasons(seasonId);
      if (foundTeams > 0) {
         throw new IllegalArgumentException("Cannot delete season with associated teams.");
      }

      final SeasonEntry season = seasonRepository.findOne(seasonId);
      final LocalDateTime startsAt = season.getStartsOn().toDateTimeAtStartOfDay().toLocalDateTime();
      final LocalDateTime endsAt = season.getEndsOn() == null ? Common.EOT
         : season.getEndsOn().toDateTimeAtStartOfDay().toLocalDateTime();
      final int foundGames = seasonRepository.countGames(startsAt, endsAt);
      if (foundGames > 0) {
         throw new IllegalArgumentException("Cannot delete season with associated games.");
      }
   }
}
