package laxstats.web.plays;

import javax.validation.Valid;

import laxstats.api.plays.CreatePenaltyTypeCommand;
import laxstats.api.plays.DeletePenaltyTypeCommand;
import laxstats.api.plays.PenaltyTypeDTO;
import laxstats.api.plays.PenaltyTypeId;
import laxstats.api.plays.UpdatePenaltyTypeCommand;
import laxstats.query.plays.PenaltyTypeEntry;
import laxstats.query.plays.PenaltyTypeQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.security.CurrentUser;

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
@RequestMapping("/penaltyTypes")
public class PenaltyTypeController {
	private final PenaltyTypeQueryRepository penaltyTypeRepository;
	private final UserQueryRepository userRepository;
	private final CommandBus commandBus;

	@Autowired
	public PenaltyTypeController(
			PenaltyTypeQueryRepository penaltyTypeRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		this.penaltyTypeRepository = penaltyTypeRepository;
		this.userRepository = userRepository;
		this.commandBus = commandBus;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", penaltyTypeRepository.findAll());
		model.addAttribute("headerName", "Name");
		model.addAttribute("headerCategory", "Category");
		model.addAttribute("headerReleasable", "Releasable");
		return "penaltyTypes/index";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable String id, Model model) {
		final PenaltyTypeEntry penaltyType = penaltyTypeRepository.findOne(id);
		model.addAttribute("penaltyType", penaltyType);
		return "penaltyTypes/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newPenaltyType(Model model) {
		final PenaltyTypeForm form = new PenaltyTypeForm();
		model.addAttribute("form", form);
		return "penaltyTypes/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createPenaltyType(
			@ModelAttribute("form") @Valid PenaltyTypeForm form,
			BindingResult bindingResult, Model model,
			@CurrentUser Object currentUser) {
		if (bindingResult.hasErrors()) {
			return "penaltyTypes/new";
		}

		final LocalDateTime now = LocalDateTime.now();
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final String email = ((org.springframework.security.core.userdetails.User) principal)
				.getUsername();
		final UserEntry user = userRepository.findByEmail(email);
		final PenaltyTypeId identifier = new PenaltyTypeId();

		final PenaltyTypeDTO dto = new PenaltyTypeDTO();
		dto.setCategory(form.getCategory());
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setDescription(form.getDescription());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setName(form.getName());
		dto.setPenaltyLength(form.getPenaltyLength());
		dto.setReleasable(form.isReleasable());

		final CreatePenaltyTypeCommand command = new CreatePenaltyTypeCommand(
				identifier, dto);
		commandBus
				.dispatch(new GenericCommandMessage<CreatePenaltyTypeCommand>(
						command));
		return "redirect:penaltyTypes";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String editPenaltyType(@PathVariable String id, Model model) {
		final PenaltyTypeEntry penaltyType = penaltyTypeRepository.findOne(id);

		final PenaltyTypeForm form = new PenaltyTypeForm();
		form.setCategory(penaltyType.getCategory());
		form.setDescription(penaltyType.getDescription());
		form.setName(penaltyType.getName());
		form.setPenaltyLength(penaltyType.getPenaltyLength());
		form.setReleasable(penaltyType.isReleasable());

		model.addAttribute("id", penaltyType.getId());
		model.addAttribute("form", form);
		return "penaltyTypes/edit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String updatePenaltyType(@PathVariable String id,
			@ModelAttribute("form") @Valid PenaltyTypeForm form, Model model) {
		final LocalDateTime now = LocalDateTime.now();
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final String email = ((org.springframework.security.core.userdetails.User) principal)
				.getUsername();
		final UserEntry user = userRepository.findByEmail(email);
		final PenaltyTypeId penaltyTypeId = new PenaltyTypeId(id);

		final PenaltyTypeDTO dto = new PenaltyTypeDTO();
		dto.setCategory(form.getCategory());
		dto.setDescription(form.getDescription());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);
		dto.setName(form.getName());
		dto.setPenaltyLength(form.getPenaltyLength());
		dto.setReleasable(form.isReleasable());

		final UpdatePenaltyTypeCommand command = new UpdatePenaltyTypeCommand(
				penaltyTypeId, dto);
		commandBus
				.dispatch(new GenericCommandMessage<UpdatePenaltyTypeCommand>(
						command));
		return "redirect:";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deletePenaltyType(@PathVariable String id) {
		final DeletePenaltyTypeCommand command = new DeletePenaltyTypeCommand(
				new PenaltyTypeId(id));
		commandBus
				.dispatch(new GenericCommandMessage<DeletePenaltyTypeCommand>(
						command));
		return "redirect:";
	}
}
