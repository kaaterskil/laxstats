package laxstats.web.violations;

import javax.validation.Valid;

import laxstats.api.violations.CreateViolationCommand;
import laxstats.api.violations.DeleteViolationCommand;
import laxstats.api.violations.UpdateViolationCommand;
import laxstats.api.violations.ViolationDTO;
import laxstats.api.violations.ViolationId;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.query.violations.ViolationEntry;
import laxstats.query.violations.ViolationQueryRepository;
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
@RequestMapping("/violations")
public class ViolationController extends ApplicationController {
	private final ViolationQueryRepository violationRepository;

	@Autowired
	public ViolationController(ViolationQueryRepository violationRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.violationRepository = violationRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", violationRepository.findAll());
		return "violations/index";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable String id, Model model) {
		final ViolationEntry violation = violationRepository.findOne(id);
		model.addAttribute("violation", violation);
		return "violations/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newViolation(Model model) {
		final ViolationForm form = new ViolationForm();
		model.addAttribute("form", form);
		return "violations/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createViolation(
			@ModelAttribute("form") @Valid ViolationForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "violations/new";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final ViolationId identifier = new ViolationId();

		final ViolationDTO dto = new ViolationDTO();
		dto.setCategory(form.getCategory());
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setDescription(form.getDescription());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setName(form.getName());
		dto.setPenaltyLength(form.getPenaltyLength());
		dto.setReleasable(form.isReleasable());

		final CreateViolationCommand command = new CreateViolationCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:violations";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String editViolation(@PathVariable String id, Model model) {
		final ViolationEntry violation = violationRepository.findOne(id);

		final ViolationForm form = new ViolationForm();
		form.setCategory(violation.getCategory());
		form.setDescription(violation.getDescription());
		form.setName(violation.getName());
		form.setPenaltyLength(violation.getPenaltyLength());
		form.setReleasable(violation.isReleasable());

		model.addAttribute("id", violation.getId());
		model.addAttribute("form", form);
		return "violations/edit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String updateViolation(@PathVariable String id,
			@ModelAttribute("form") @Valid ViolationForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "violations/edit";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final ViolationId violationId = new ViolationId(id);

		final ViolationDTO dto = new ViolationDTO();
		dto.setCategory(form.getCategory());
		dto.setDescription(form.getDescription());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setName(form.getName());
		dto.setPenaltyLength(form.getPenaltyLength());
		dto.setReleasable(form.isReleasable());

		final UpdateViolationCommand command = new UpdateViolationCommand(
				violationId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteViolation(@PathVariable String id) {
		final DeleteViolationCommand command = new DeleteViolationCommand(
				new ViolationId(id));
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:";
	}
}
