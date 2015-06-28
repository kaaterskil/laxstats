package laxstats.web.seasons;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@Controller
public class SeasonController extends ApplicationController {
   private static final Logger logger = LoggerFactory.getLogger(SeasonController.class);
   private static final String PACKAGE_NAME = SeasonController.class.getPackage().getName();

   private final SeasonQueryRepository seasonRepository;

   @Autowired
   private SeasonFormValidator seasonValidator;

   @Autowired
   public SeasonController(SeasonQueryRepository seasonRepository,
      UserQueryRepository userRepository, CommandBus commandBus) {
      super(userRepository, commandBus);
      this.seasonRepository = seasonRepository;
   }

   @InitBinder(value = "seasonForm")
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(seasonValidator);
   }

   /*---------- Action methods ----------*/

   @RequestMapping(value = "/admin/seasons", method = RequestMethod.GET)
   public String seasonIndex(Model model) {
      final Iterable<SeasonEntry> list =
         seasonRepository.findAll(new Sort(Direction.DESC, "startsOn"));
      model.addAttribute("seasons", list);
      return "seasons/index";
   }

   @RequestMapping(value = "/admin/seasons", method = RequestMethod.POST)
   public String createSeason(@Valid SeasonForm seasonForm, BindingResult result,
      RedirectAttributes redirectAttributes)
   {
      final String proc = PACKAGE_NAME + ".createSeason.";

      logger.debug("Entering: " + proc + "10");
      if (result.hasErrors()) {
         logger.debug("Returning errors: " + proc + "20");
         return "seasons/newSeason";
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SeasonId identifier = new SeasonId();
      final SeasonDTO dto = new SeasonDTO(identifier, seasonForm.getDescription(),
         seasonForm.getStartsOn(), seasonForm.getEndsOn(), now, user, now, user);
      logger.debug(proc + "30");

      try {
         final CreateSeason payload = new CreateSeason(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      }
      catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/seasons";
   }

   @RequestMapping(value = "/admin/seasons/{seasonId}", method = RequestMethod.GET)
   public String showSeason(@PathVariable("seasonId") SeasonEntry season, Model model) {
      model.addAttribute("item", season);
      return "seasons/showSeason";
   }

   @RequestMapping(value = "/admin/seasons/{seasonId}", method = RequestMethod.PUT)
   public String updateSeason(@PathVariable String seasonId, @Valid SeasonForm seasonForm,
      BindingResult bindingResult, RedirectAttributes redirectAttributes)
   {
      final String proc = PACKAGE_NAME + ".updateSeason.";

      logger.debug("Entering: " + proc + "10");
      if (bindingResult.hasErrors()) {
         logger.debug("Returning errors: " + proc + "20");
         return "seasons/editSeason";
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SeasonId identifier = new SeasonId(seasonId);
      final SeasonDTO dto = new SeasonDTO(identifier, seasonForm.getDescription(),
         seasonForm.getStartsOn(), seasonForm.getEndsOn(), now, user);
      logger.debug(proc + "30");

      try {
         final UpdateSeason payload = new UpdateSeason(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      }
      catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/seasons";
   }

   @RequestMapping(value = "/admin/seasons/{seasonId}", method = RequestMethod.DELETE)
   public String deleteSeason(@PathVariable String seasonId, RedirectAttributes redirectAttributes) {
      final String proc = PACKAGE_NAME + ".deleteSeason.";
      final SeasonId identifier = new SeasonId(seasonId);

      logger.debug("Entering: " + proc + "10");
      try {
         checkDelete(seasonId);
         logger.debug(proc + "20");

         final DeleteSeason payload = new DeleteSeason(identifier);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      }
      catch (final Exception e) {
         logger.debug(proc + "30");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "40");
      return "redirect:/admin/seasons";
   }

   @RequestMapping(value = "/admin/seasons/new", method = RequestMethod.GET)
   public String newSeason(SeasonForm seasonForm, Model model, HttpServletRequest request) {
      model.addAttribute("seasonForm", seasonForm);
      model.addAttribute("back", request.getHeader("Referer"));
      return "seasons/newSeason";
   }

   @RequestMapping(value = "/admin/seasons/{seasonId}/edit", method = RequestMethod.GET)
   public String editSeason(@PathVariable("seasonId") SeasonEntry season, Model model,
      HttpServletRequest request)
   {
      final SeasonForm form = new SeasonForm();
      form.setId(season.getId());
      form.setDescription(season.getDescription());
      form.setStartsOn(season.getStartsOn());
      form.setEndsOn(season.getEndsOn());

      model.addAttribute("seasonForm", form);
      model.addAttribute("seasonId", season.getId());
      model.addAttribute("back", request.getHeader("Referer"));
      return "seasons/editSeason";
   }

   /*---------- Ajax methods ----------*/

   @RequestMapping(value = "/api/seasons", method = RequestMethod.GET)
   public @ResponseBody Iterable<SeasonEntry> apiSeasons() {
      return seasonRepository.findAll(new Sort(Direction.DESC, "startsOn"));
   }

   @RequestMapping(value = "/api/seasons/{seasonId}", method = RequestMethod.GET)
   public @ResponseBody SeasonInfo getSeason(@PathVariable("seasonId") SeasonEntry season) {
      return new SeasonInfo(season.getId(), season.getDescription(),
         season.getStartsOn().toString("yyyy-MM-dd"), season.getEndsOn().toString("yyyy-MM-dd"));
   }

   /*---------- Utilities ----------*/

   private void checkDelete(String seasonId) {
      final String proc = PACKAGE_NAME + ".checkDelete.";
      int found = 0;

      logger.debug("Entering: " + proc + "10");

      // Check that there are no values in TeamSeasons or Games
      found = seasonRepository.countTeamSeasons(seasonId);
      if (found > 0) {
         throw new IllegalArgumentException("Cannot delete season with associated teams.");
      }
      logger.debug(proc + "20");

      final SeasonEntry season = seasonRepository.findOne(seasonId);
      logger.debug(proc + "30");

      final LocalDateTime startsAt = season.getStartsOn().toDateTimeAtStartOfDay().toLocalDateTime();
      final LocalDateTime endsAt = season.getEndsOn() == null ? Common.EOT
         : season.getEndsOn().toDateTimeAtStartOfDay().toLocalDateTime();
      final int foundGames = seasonRepository.countGames(startsAt, endsAt);
      if (foundGames > 0) {
         throw new IllegalArgumentException("Cannot delete season with associated games.");
      }
      logger.debug("Leaving: " + proc + "40");
   }
}
