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
@RequestMapping("/penaltyTypes")
public class PenaltyTypeController extends ApplicationController {
	private final PenaltyTypeQueryRepository penaltyTypeRepository;

	@Autowired
	public PenaltyTypeController(
			PenaltyTypeQueryRepository penaltyTypeRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.penaltyTypeRepository = penaltyTypeRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", penaltyTypeRepository.findAll());
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
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "penaltyTypes/new";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
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
				.dispatch(new GenericCommandMessage<>(
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
			@ModelAttribute("form") @Valid PenaltyTypeForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "penaltyTypes/edit";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
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
				.dispatch(new GenericCommandMessage<>(
						command));
		return "redirect:";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deletePenaltyType(@PathVariable String id) {
		final DeletePenaltyTypeCommand command = new DeletePenaltyTypeCommand(
				new PenaltyTypeId(id));
		commandBus
				.dispatch(new GenericCommandMessage<>(
						command));
		return "redirect:";
	}
}
