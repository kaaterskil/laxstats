package laxstats.web.seasons;

import laxstats.api.seasons.*;
import laxstats.query.season.SeasonEntry;
import laxstats.query.season.SeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
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
		model.addAttribute("form", form);
		return "seasons/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createSeason(@ModelAttribute("form") @Valid SeasonForm form,
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
		model.addAttribute("form", form);
		return "seasons/edit";
	}

	@RequestMapping(value = "/{seasonId}", method = RequestMethod.PUT)
	public String updateSeason(@ModelAttribute("form") @Valid SeasonForm form,
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

	private UserEntry getCurrentUser() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final String email = ((org.springframework.security.core.userdetails.User) principal)
				.getUsername();
		return userRepository.findByEmail(email);
	}
}
