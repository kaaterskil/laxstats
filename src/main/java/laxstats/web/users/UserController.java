package laxstats.web.users;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.users.CreateUserCommand;
import laxstats.api.users.UpdateUserCommand;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserId;
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
	private final CommandBus commandBus;

	@Autowired
	public UserController(UserQueryRepository userRepository,
			CommandBus commandBus) {
		this.userRepository = userRepository;
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
		final UserEntry user = userRepository.findByEmail(principal.toString());
		final UserId userId = new UserId();

		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String encodedPassword = encoder.encode(form.getPassword());

		final UserDTO dto = new UserDTO();
		dto.setCreatedAt(now);
		dto.setCreatedBy(user.getId());
		dto.setEmail(form.getEmail());
		dto.setEnabled(form.isEnabled());
		dto.setEncodedPassword(encodedPassword);
		dto.setFirstName(form.getFirstName());
		dto.setIpAddress(request.getRemoteAddr());
		dto.setLastName(form.getLastName());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user.getId());
		dto.setRole(form.getRole());
		dto.setTeamId(form.getTeamId());

		final CreateUserCommand payload = new CreateUserCommand(userId, dto);
		commandBus.dispatch(new GenericCommandMessage<CreateUserCommand>(
				payload));
		return "redirect:/users";
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
		form.setTeamId(user.getTeamId());

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
		final UserEntry user = userRepository.findByEmail(principal.toString());

		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String encodedPassword = encoder.encode(form.getPassword());

		final UserDTO dto = new UserDTO();
		dto.setCreatedAt(now);
		dto.setCreatedBy(user.getId());
		dto.setEmail(form.getEmail());
		dto.setEnabled(form.isEnabled());
		dto.setEncodedPassword(encodedPassword);
		dto.setFirstName(form.getFirstName());
		dto.setIpAddress(request.getRemoteAddr());
		dto.setLastName(form.getLastName());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user.getId());
		dto.setRole(form.getRole());
		dto.setTeamId(form.getTeamId());

		final UpdateUserCommand payload = new UpdateUserCommand(new UserId(
				userId), dto);
		commandBus.dispatch(new GenericCommandMessage<UpdateUserCommand>(
				payload));
		return "redirect:/users";
	}
}
