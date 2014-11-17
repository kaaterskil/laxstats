package laxstats.web.seasons;

import javax.validation.Valid;

import laxstats.api.seasons.CreateSeasonCommand;
import laxstats.api.seasons.SeasonId;
import laxstats.query.season.Season;
import laxstats.query.season.SeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seasons")
public class SeasonController {

	private SeasonQueryRepository seasonRepository;
	private UserQueryRepository userRepository;
	private CommandBus commandBus;

	@Autowired
	public SeasonController(SeasonQueryRepository seasonRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		this.seasonRepository = seasonRepository;
		this.userRepository = userRepository;
		this.commandBus = commandBus;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", seasonRepository.findAll());
		return "seasons/list";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newSeason(Model model) {
		SeasonForm form = new SeasonForm();
		model.addAttribute("form", form);
		return "seasons/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createSeason(@ModelAttribute("form") @Valid SeasonForm seasonForm,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "seasons/new";
		}

		LocalDateTime now = LocalDateTime.now();
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		UserEntry user = userRepository.findByEmail(principal.toString());

		CreateSeasonCommand command = new CreateSeasonCommand(new SeasonId(),
				seasonForm.getDescription(), seasonForm.getStartsOn(),
				seasonForm.getEndsOn(), user.getId(), now, user.getId(), now);
		commandBus.dispatch(new GenericCommandMessage<CreateSeasonCommand>(
				command));
		return "redirect:/seasons";
	}

	@RequestMapping(value = "/{seasonId}", method = RequestMethod.GET)
	public String showSeason(@PathVariable String seasonId, Model model) {
		Season season = seasonRepository.findOne(seasonId);
		model.addAttribute("item", season);
		return "seasons/show";
	}
	
	@RequestMapping(value = "/{seasonId}/edit", method = RequestMethod.GET)
	public String editSeason(@PathVariable String seasonId, Model model) {
		Season season = seasonRepository.findOne(seasonId);
		
		SeasonForm form = new SeasonForm();
		form.setDescription(season.getDescription());
		form.setStartsOn(season.getStartsOn());
		form.setEndsOn(season.getEndsOn());
		model.addAttribute("form", form);
		return "seasons/edit";
	}
}
