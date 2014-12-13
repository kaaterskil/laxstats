package laxstats.web.teams;

import javax.validation.Valid;

import laxstats.api.teams.CreateTeamCommand;
import laxstats.api.teams.DeleteTeamCommand;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.UpdateTeamCommand;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private final TeamQueryRepository teamRepository;
	private final UserQueryRepository userRepository;
	private final SiteQueryRepository siteRepository;
	private final CommandBus commandBus;

	@Autowired
	public TeamController(TeamQueryRepository teamRepository,
			SiteQueryRepository siteRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		this.teamRepository = teamRepository;
		this.siteRepository = siteRepository;
		this.userRepository = userRepository;
		this.commandBus = commandBus;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("teams", teamRepository.findAll());
		return "teams/index";
	}

	@RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
	public String show(@PathVariable String teamId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);
		model.addAttribute("team", team);
		return "teams/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newTeam(Model model) {
		final TeamForm form = new TeamForm();
		model.addAttribute("form", form);
		return "teams/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createTeam(@ModelAttribute("form") @Valid TeamForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "teams/new";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamId identifier = new TeamId();

		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String encodedPassword = encoder.encode(form.getPassword());

		final SiteEntry homeSite = siteRepository.findOne(form.getHomeSiteId());

		final TeamDTO dto = new TeamDTO();
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setEncodedPassword(encodedPassword);
		dto.setGender(form.getGender());
		dto.setHomeSite(homeSite);
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setName(form.getName());
		dto.setTeamId(identifier);

		final CreateTeamCommand command = new CreateTeamCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(
				command));
		return "redirect:/teams";
	}

	@RequestMapping(value = "/{teamId}/edit", method = RequestMethod.GET)
	public String editTeam(@PathVariable String teamId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);

		final TeamForm form = new TeamForm();
		form.setGender(team.getGender());
		form.setHomeSiteId(team.getHomeSite().toString());
		form.setName(team.getName());

		model.addAttribute("form", form);
		return "teams/edit";
	}

	@RequestMapping(value = "/{teamId}", method = RequestMethod.PUT)
	public String updateTeam(@PathVariable String teamId,
			@ModelAttribute("form") @Valid TeamForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "teams/edit";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamId identifier = new TeamId(teamId);

		final SiteEntry homeSite = siteRepository.findOne(form.getHomeSiteId());

		final TeamDTO dto = new TeamDTO();
		dto.setGender(form.getGender());
		dto.setHomeSite(homeSite);
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setName(form.getName());

		final UpdateTeamCommand command = new UpdateTeamCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(
				command));
		return "redirect:";
	}

	@RequestMapping(value = "/{teamId}", method = RequestMethod.DELETE)
	public String deleteTeam(@PathVariable String teamId) {
		final TeamId identifier = new TeamId(teamId);
		final DeleteTeamCommand command = new DeleteTeamCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(
				command));
		return "redirect:";
	}

	private UserEntry getCurrentUser() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final String email = ((org.springframework.security.core.userdetails.User) principal)
				.getUsername();
		return userRepository.findByEmail(email);
	}
}
