package laxstats.web.seasons;

import javax.validation.Valid;

import laxstats.api.seasons.CreateSeasonCommand;
import laxstats.api.seasons.DeleteSeasonCommand;
import laxstats.api.seasons.SeasonDTO;
import laxstats.api.seasons.SeasonId;
import laxstats.api.seasons.UpdateSeasonCommand;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
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

@Controller
public class SeasonController extends ApplicationController {
	private final SeasonQueryRepository seasonRepository;
	private SeasonFormValidator seasonValidator;

	@InitBinder(value = "SeasonForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(seasonValidator);
	}

	@Autowired
	public SeasonController(SeasonQueryRepository seasonRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.seasonRepository = seasonRepository;
	}

	@Autowired
	public void setSeasonValidator(SeasonFormValidator seasonValidator) {
		this.seasonValidator = seasonValidator;
	}

	/*---------- Action methods ----------*/

	@RequestMapping(value = "/seasons", method = RequestMethod.GET)
	public String index(Model model) {
		final Iterable<SeasonEntry> list = seasonRepository.findAll(new Sort(
				Direction.DESC, "startsOn"));
		model.addAttribute("items", list);
		return "seasons/index";
	}

	@RequestMapping(value = "/seasons", method = RequestMethod.POST)
	public String createSeason(@Valid SeasonForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "seasons/newSeason";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final SeasonId identifier = new SeasonId();
		final LocalDate endsOn = testEndsOn(form.getEndsOn());

		final SeasonDTO dto = new SeasonDTO(identifier, form.getDescription(),
				form.getStartsOn(), endsOn, now, user, now, user);

		final CreateSeasonCommand payload = new CreateSeasonCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/seasons";
	}

	@RequestMapping(value = "/seasons/{seasonId}", method = RequestMethod.GET)
	public String showSeason(@PathVariable("seasonId") SeasonEntry season,
			Model model) {
		model.addAttribute("item", season);
		return "seasons/showSeason";
	}

	@RequestMapping(value = "/seasons/{seasonId}", method = RequestMethod.PUT)
	public String updateSeason(@PathVariable String seasonId,
			@Valid SeasonForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "seasons/editSeason";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final SeasonId identifier = new SeasonId(seasonId);
		final LocalDate endsOn = testEndsOn(form.getEndsOn());

		final SeasonDTO dto = new SeasonDTO(identifier, form.getDescription(),
				form.getStartsOn(), endsOn, now, user);

		final UpdateSeasonCommand payload = new UpdateSeasonCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/seasons";
	}

	@RequestMapping(value = "/seasons/{seasonId}", method = RequestMethod.DELETE)
	public String deleteSeason(@PathVariable String seasonId) {
		final SeasonId identifier = new SeasonId(seasonId);

		final DeleteSeasonCommand payload = new DeleteSeasonCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/seasons";
	}

	@RequestMapping(value = "/seasons/new", method = RequestMethod.GET)
	public String newSeason(Model model) {
		final SeasonForm form = new SeasonForm();
		model.addAttribute("seasonForm", form);
		return "seasons/newSeason";
	}

	@RequestMapping(value = "/seasons/{seasonId}/edit", method = RequestMethod.GET)
	public String editSeason(@PathVariable("seasonId") SeasonEntry season,
			Model model) {
		final SeasonForm form = new SeasonForm();
		form.setDescription(season.getDescription());
		form.setStartsOn(season.getStartsOn());
		form.setEndsOn(season.getEndsOn());

		model.addAttribute("seasonForm", form);
		model.addAttribute("seasonId", season.getId());
		return "seasons/editSeason";
	}

	/*---------- Ajax methods ----------*/

	@RequestMapping(value = "/api/seasons/{seasonId}", method = RequestMethod.GET)
	public @ResponseBody SeasonInfo getSeason(
			@PathVariable("seasonId") SeasonEntry season) {
		return new SeasonInfo(season.getId(), season.getDescription(), season
				.getStartsOn().toString("yyyy-MM-dd"), season.getEndsOn()
				.toString("yyyy-MM-dd"));
	}

	/*---------- Utilities ----------*/

	private LocalDate testEndsOn(LocalDate endsOn) {
		LocalDate result = endsOn;
		if (result == null) {
			result = new LocalDate(Long.MAX_VALUE);
		}
		return result;
	}
}
