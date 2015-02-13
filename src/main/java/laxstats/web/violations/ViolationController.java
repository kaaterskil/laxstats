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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViolationController extends ApplicationController {
	private final ViolationQueryRepository violationRepository;

	@Autowired
	public ViolationController(ViolationQueryRepository violationRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.violationRepository = violationRepository;
	}

	/*---------- Action methods ----------*/

	@RequestMapping(value = "/admin/violations", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("violations", violationRepository.findAll());
		return "violations/index";
	}

	@RequestMapping(value = "/admin/violations", method = RequestMethod.POST)
	public String createViolation(@Valid ViolationForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "violations/newViolation";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final ViolationId identifier = new ViolationId();

		final ViolationDTO dto = new ViolationDTO(identifier.toString(),
				form.getName(), form.getDescription(), form.getCategory(),
				form.getPenaltyLength(), form.isReleasable(), now, user, now,
				user);

		final CreateViolationCommand command = new CreateViolationCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/admin/violations";
	}

	@RequestMapping(value = "/admin/violations/{violationId}", method = RequestMethod.GET)
	public String show(@PathVariable("violationId") ViolationEntry violation,
			Model model) {
		model.addAttribute("violation", violation);
		return "violations/show";
	}

	@RequestMapping(value = "/admin/violations/{violationId}", method = RequestMethod.PUT)
	public String updateViolation(@PathVariable String violationId,
			@Valid ViolationForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "violations/editViolation";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final ViolationId identifier = new ViolationId(violationId);

		final ViolationDTO dto = new ViolationDTO(violationId, form.getName(),
				form.getDescription(), form.getCategory(),
				form.getPenaltyLength(), form.isReleasable(), now, user);

		final UpdateViolationCommand command = new UpdateViolationCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/admin/violations";
	}

	@RequestMapping(value = "/admin/violations/{violationId}", method = RequestMethod.DELETE)
	public String deleteViolation(@PathVariable String violationId) {
		final DeleteViolationCommand command = new DeleteViolationCommand(
				new ViolationId(violationId));
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/admin/violations";
	}

	@RequestMapping(value = "/admin/violations/new", method = RequestMethod.GET)
	public String newViolation(Model model) {
		final ViolationForm form = new ViolationForm();
		model.addAttribute("violationForm", form);
		return "violations/newViolation";
	}

	@RequestMapping(value = "/admin/violations/{violationId}/edit", method = RequestMethod.GET)
	public String editViolation(
			@PathVariable("violationId") ViolationEntry violation, Model model) {

		final ViolationForm form = new ViolationForm();
		form.setCategory(violation.getCategory());
		form.setDescription(violation.getDescription());
		form.setName(violation.getName());
		form.setPenaltyLength(violation.getPenaltyLength());
		form.setReleasable(violation.isReleasable());

		model.addAttribute("violationId", violation.getId());
		model.addAttribute("violationForm", form);
		return "violations/editViolation";
	}
}
