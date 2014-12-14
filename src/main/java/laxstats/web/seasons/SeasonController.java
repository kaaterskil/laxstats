package laxstats.web.seasons;

import laxstats.api.seasons.*;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/seasons")
public class SeasonController extends ApplicationController {
	private final SeasonQueryRepository seasonRepository;

	@Autowired
	public SeasonController(SeasonQueryRepository seasonRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.seasonRepository = seasonRepository;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new SeasonFormValidator());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", seasonRepository.findAll());
		return "seasons/index";
	}

	@RequestMapping(value = "/{seasonId}", method = RequestMethod.GET)
	public String showSeason(@PathVariable String seasonId, Model model) {
		SeasonEntry season = seasonRepository.findOne(seasonId);
		model.addAttribute("item", season);
		return "seasons/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newSeason(Model model) {
		SeasonForm form = new SeasonForm();
		model.addAttribute("seasonForm", form);
		return "seasons/newSeason";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createSeason(@ModelAttribute("seasonForm") @Valid SeasonForm form,
							   BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "seasons/new";
		}
		LocalDateTime now = LocalDateTime.now();
		UserEntry user = getCurrentUser();
		SeasonId identifier = new SeasonId();

		SeasonDTO dto = new SeasonDTO();
		dto.setSeasonId(identifier);
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setDescription(form.getDescription());
		dto.setEndsOn(form.getEndsOn());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setStartsOn(form.getStartsOn());

		CreateSeasonCommand payload = new CreateSeasonCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/seasons";
	}
	
	@RequestMapping(value = "/{seasonId}/edit", method = RequestMethod.GET)
	public String editSeason(@PathVariable String seasonId, Model model) {
		SeasonEntry season = seasonRepository.findOne(seasonId);
		
		SeasonForm form = new SeasonForm();
		form.setDescription(season.getDescription());
		form.setStartsOn(season.getStartsOn());
		form.setEndsOn(season.getEndsOn());
		model.addAttribute("seasonForm", form);
		return "seasons/edit";
	}

	@RequestMapping(value = "/{seasonId}", method = RequestMethod.PUT)
	public String updateSeason(@ModelAttribute("seasonForm") @Valid SeasonForm form,
							   @PathVariable String seasonId,
							   BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "seasons/edit";
		}
		LocalDateTime now = LocalDateTime.now();
		UserEntry user = getCurrentUser();
		SeasonId identifier = new SeasonId(seasonId);

		SeasonDTO dto = new SeasonDTO();
		dto.setSeasonId(identifier);
		dto.setDescription(form.getDescription());
		dto.setEndsOn(form.getEndsOn());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setStartsOn(form.getStartsOn());

		UpdateSeasonCommand payload = new UpdateSeasonCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/seasons";
	}

	@RequestMapping(value = "/{seasonId}", method = RequestMethod.DELETE)
	public String deleteSeason(@PathVariable String seasonId) {
		SeasonId identifier = new SeasonId(seasonId);
		DeleteSeasonCommand payload = new DeleteSeasonCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/seasons";
	}
}
