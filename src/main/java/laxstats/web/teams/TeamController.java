package laxstats.web.teams;

import javax.validation.Valid;

import laxstats.api.teams.CreateTeamCommand;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamId;
import laxstats.query.teams.Team;
import laxstats.query.teams.TeamQueryRepository;
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

@Controller
@RequestMapping("/teams")
public class TeamController {
	private TeamQueryRepository teamRepository;
	private UserQueryRepository userRepository;
	private CommandBus commandBus;

	@Autowired
	public TeamController(TeamQueryRepository teamRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		this.teamRepository = teamRepository;
		this.userRepository = userRepository;
		this.commandBus = commandBus;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", teamRepository.findAll());
		return "teams/index";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newTeam(Model model) {
		TeamForm form = new TeamForm();
		model.addAttribute("form", form);
		return "teams/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createTeam(@ModelAttribute("form") @Valid TeamForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "teams/new";
		}
		LocalDateTime now = LocalDateTime.now();
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		UserEntry user = userRepository.findByEmail(principal.toString());
		TeamId identifier = new TeamId();

		TeamDTO dto = new TeamDTO(identifier, form.getName(), form.getGender(),
				form.getHomeSiteId(), form.getPassword(), now, user.getId(),
				now, user.getId());
		CreateTeamCommand command = new CreateTeamCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<CreateTeamCommand>(
				command));
		return "redirect:/teams";
	}
	
	@RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
	public String showTeam(@PathVariable String teamId, Model model) {
		Team team = teamRepository.findOne(teamId);
		model.addAttribute("item", team);
		return "teams/show";
	}

}
