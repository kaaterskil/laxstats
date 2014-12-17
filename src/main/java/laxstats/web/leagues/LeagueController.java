package laxstats.web.leagues;

import javax.validation.Valid;

import laxstats.api.leagues.CreateLeagueCommand;
import laxstats.api.leagues.DeleteLeagueCommand;
import laxstats.api.leagues.LeagueDTO;
import laxstats.api.leagues.LeagueId;
import laxstats.api.leagues.UpdateLeagueCommand;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.leagues.LeagueQueryRepository;
import laxstats.query.teams.TeamQueryRepository;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/leagues")
public class LeagueController extends ApplicationController {
	private final LeagueQueryRepository leagueRepository;
	private final TeamQueryRepository teamRepository;

	@Autowired
	public LeagueController(UserQueryRepository userRepository,
			CommandBus commandBus, LeagueQueryRepository leagueRepository,
			TeamQueryRepository teamRepository) {
		super(userRepository, commandBus);
		this.leagueRepository = leagueRepository;
		this.teamRepository = teamRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("leagues", leagueRepository.findAll());
		return "leagues/index";
	}

	@RequestMapping(value = "/{leagueId}", method = RequestMethod.GET)
	public String showLeague(@PathVariable String leagueId, Model model) {
		final LeagueEntry league = leagueRepository.findOne(leagueId);
		model.addAttribute("league", league);
		return "leagues/showLeague";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newLeague(Model model) {
		final LeagueForm form = new LeagueForm();
		model.addAttribute("form", form);
		return "leagues/newLeague";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createLeague(@ModelAttribute("form") @Valid LeagueForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "leagues/new";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final LeagueId identifier = new LeagueId();
		LeagueEntry parent = null;
		if (form.getParentId() != null) {
			parent = leagueRepository.findOne(form.getParentId());
		}

		final LeagueDTO dto = new LeagueDTO(form.getName(), form.getLevel(),
				form.getDescription(), parent, now, user, now, user);
		final CreateLeagueCommand payload = new CreateLeagueCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/leagues/index";
	}

	@RequestMapping(value = "/{leagueId}/edit", method = RequestMethod.GET)
	public String editLeague(@PathVariable String leagueId, Model model) {
		final LeagueEntry league = leagueRepository.findOne(leagueId);

		final LeagueForm form = new LeagueForm();
		form.setName(league.getName());
		form.setLevel(league.getLevel());
		form.setDescription(league.getDescription());
		if (league.getParent() != null) {
			form.setParentId(league.getParent().getId());
		}

		model.addAttribute("form", form);
		return "leagues/editLeague";
	}

	@RequestMapping(value = "/{leagueId}", method = RequestMethod.PUT)
	public String updateLeague(@ModelAttribute("form") @Valid LeagueForm form,
			@PathVariable String leagueId, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "leagues/edit";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final LeagueId identifier = new LeagueId(leagueId);
		LeagueEntry parent = null;
		if (form.getParentId() != null) {
			parent = leagueRepository.findOne(form.getParentId());
		}

		final LeagueDTO dto = new LeagueDTO(form.getName(), form.getLevel(),
				form.getDescription(), parent, null, null, now, user);
		final UpdateLeagueCommand payload = new UpdateLeagueCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/leagues/index";
	}

	@RequestMapping(value = "/{leagueId}", method = RequestMethod.DELETE)
	public String deleteLeague(@PathVariable String leagueId) {
		final LeagueId identifier = new LeagueId(leagueId);
		final DeleteLeagueCommand payload = new DeleteLeagueCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/leagues/index";
	}
}
