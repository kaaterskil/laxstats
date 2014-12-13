package laxstats.web.users;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.users.CreateUserCommand;
import laxstats.api.users.UpdateUserCommand;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserId;
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
@RequestMapping("/users")
public class UserController {
	private final UserQueryRepository userRepository;
	private final TeamQueryRepository teamRepository;
	private final CommandBus commandBus;

	@Autowired
	public UserController(UserQueryRepository userRepository,
			TeamQueryRepository teamRepository, CommandBus commandBus) {
		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
		this.commandBus = commandBus;
	}

	// ---------- Actions ----------//

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "users/index";
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String show(@PathVariable String userId, Model model) {
		final UserEntry user = userRepository.findOne(userId);
		model.addAttribute("user", user);
		return "users/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newUser(Model model) {
		final UserForm form = new UserForm();
		model.addAttribute("form", form);
		return "users/newUser";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createUser(@ModelAttribute("form") @Valid UserForm form,
			BindingResult bindingResult, Model model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "users/newUser";
		}

		final LocalDateTime now = LocalDateTime.now();
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final String email = ((org.springframework.security.core.userdetails.User) principal)
				.getUsername();
		final UserEntry user = userRepository.findByEmail(email);
		final UserId identifier = new UserId();

		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String encodedPassword = encoder.encode(form.getPassword());

		TeamEntry team = null;
		if (form.getTeamId() != null) {
			team = teamRepository.findOne(form.getTeamId());
		}

		final UserDTO dto = new UserDTO();
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setEmail(form.getEmail());
		dto.setEnabled(form.isEnabled());
		dto.setEncodedPassword(encodedPassword);
		dto.setFirstName(form.getFirstName());
		dto.setIpAddress(request.getRemoteAddr());
		dto.setLastName(form.getLastName());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setRole(form.getRole());
		dto.setTeam(team);

		final CreateUserCommand payload = new CreateUserCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<CreateUserCommand>(
				payload));
		return "redirect:users";
	}

	@RequestMapping(value = "/{userId}/edit", method = RequestMethod.GET)
	public String editUser(@PathVariable String userId, Model model) {
		final UserEntry user = userRepository.findOne(userId);

		final UserForm form = new UserForm();
		form.setEmail(user.getEmail());
		form.setEnabled(user.isEnabled());
		form.setFirstName(user.getFirstName());
		form.setLastName(user.getLastName());
		form.setPassword(user.getEncodedPassword());
		form.setRole(user.getRole());
		if (user.getTeam() != null) {
			form.setTeamId(user.getTeam().toString());
		}

		model.addAttribute("id", user.getId());
		model.addAttribute("form", form);
		return "users/editUser";
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public String updateUser(@ModelAttribute("form") @Valid UserForm form,
			@PathVariable String userId, BindingResult bindingResult,
			Model model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "users/editUser";
		}
		final LocalDateTime now = LocalDateTime.now();
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final String email = ((org.springframework.security.core.userdetails.User) principal)
				.getUsername();
		final UserEntry user = userRepository.findByEmail(email);

		TeamEntry team = null;
		if (form.getTeamId() != null) {
			team = teamRepository.findOne(form.getTeamId());
		}

		final UserDTO dto = new UserDTO();
		dto.setEmail(form.getEmail());
		dto.setEnabled(form.isEnabled());
		dto.setFirstName(form.getFirstName());
		dto.setLastName(form.getLastName());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setRole(form.getRole());
		dto.setTeam(team);

		final UpdateUserCommand payload = new UpdateUserCommand(new UserId(
				userId), dto);
		commandBus.dispatch(new GenericCommandMessage<UpdateUserCommand>(
				payload));
		return "redirect:";
	}
}
