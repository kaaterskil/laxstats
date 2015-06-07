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

import laxstats.api.seasons.CreateSeasonCommand;
import laxstats.api.seasons.SeasonDTO;
import laxstats.api.seasons.SeasonId;
import laxstats.api.seasons.UpdateSeasonCommand;
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
    * @param form
    * @param result
    * @return
    */
   @RequestMapping(method = RequestMethod.POST)
   public SeasonInfo createSeason(@Valid SeasonInfo resource, BindingResult result) {
      if (result.hasErrors()) {
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

      final CreateSeasonCommand payload = new CreateSeasonCommand(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

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

      final UpdateSeasonCommand payload = new UpdateSeasonCommand(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return resource;
   }
}
