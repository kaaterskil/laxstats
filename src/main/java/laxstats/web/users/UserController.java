package laxstats.web.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.users.CreateUserCommand;
import laxstats.api.users.UpdateUserCommand;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserId;
import laxstats.api.users.UserRole;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController extends ApplicationController {
	private final TeamQueryRepository teamRepository;

	@Autowired
	public UserController(UserQueryRepository userRepository,
			TeamQueryRepository teamRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.teamRepository = teamRepository;
	}

	/*---------- Actions ----------*/

	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "users/index";
	}

	@RequestMapping(value = "/admin/users", method = RequestMethod.POST)
	public String createUser(@Valid UserForm form, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "users/newUser";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final UserId identifier = new UserId();

		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String encodedPassword = encoder.encode(form.getPassword());

		TeamEntry team = null;
		if (form.getTeamId() != null) {
			team = teamRepository.findOne(form.getTeamId());
		}

		final UserDTO dto = new UserDTO(identifier.toString(), form.getEmail(),
				encodedPassword, form.getFirstName(), form.getLastName(), team,
				request.getRemoteAddr(), true, form.getRole(), now, user, now,
				user);

		final CreateUserCommand payload = new CreateUserCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/admin/users";
	}

	@RequestMapping(value = "/admin/users/{userId}", method = RequestMethod.GET)
	public String show(@PathVariable("userId") UserEntry user, Model model) {
		model.addAttribute("user", user);
		return "users/show";
	}

	@RequestMapping(value = "/admin/users/{userId}", method = RequestMethod.PUT)
	public String updateUser(@PathVariable("userId") UserEntry user,
			@Valid UserForm form, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "users/editUser";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry modifier = getCurrentUser();
		final UserId identifier = new UserId(user.getId());

		TeamEntry team = null;
		if (form.getTeamId() != null) {
			team = teamRepository.findOne(form.getTeamId());
		}

		String encodedPassword = user.getEncodedPassword();
		final String rawPassword = form.getPassword();
		if (rawPassword != null) {
			final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			encodedPassword = encoder.encode(rawPassword);
		}

		final UserDTO dto = new UserDTO(user.getId(), form.getEmail(),
				encodedPassword, form.getFirstName(), form.getLastName(), team,
				request.getRemoteAddr(), form.isEnabled(), form.getRole(), now,
				modifier);

		final UpdateUserCommand payload = new UpdateUserCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/admin/users";
	}

	@RequestMapping(value = "/admin/users/new", method = RequestMethod.GET)
	public String newUser(Model model) {
		final UserForm form = new UserForm();
		form.setRole(UserRole.ROLE_COACH);
		form.setTeams(getTeams());

		model.addAttribute("userForm", form);
		return "users/newUser";
	}

	@RequestMapping(value = "/admin/users/{userId}/edit", method = RequestMethod.GET)
	public String editUser(@PathVariable("userId") UserEntry user, Model model) {
		final UserForm form = new UserForm();

		form.setFirstName(user.getFirstName());
		form.setLastName(user.getLastName());
		form.setEmail(user.getEmail());
		form.setRole(user.getRole());
		form.setEnabled(user.isEnabled());
		if (user.getTeam() != null) {
			form.setTeamId(user.getTeam().getId());
		}
		form.setTeams(getTeams());

		model.addAttribute("userId", user.getId());
		model.addAttribute("userForm", form);
		return "users/editUser";
	}

	/*---------- Utilities ----------*/

	private Map<String, String> getTeams() {
		final Iterable<TeamEntry> teams = teamRepository.findAll(teamSorter());

		final Map<String, String> result = new HashMap<>();
		for (final TeamEntry each : teams) {
			result.put(each.getId(), each.getTitle());
		}
		return result;
	}

	private Sort teamSorter() {
		final List<Sort.Order> sort = new ArrayList<>();
		sort.add(new Sort.Order("affiliation.name"));
		sort.add(new Sort.Order("name"));
		return new Sort(sort);
	}
}
