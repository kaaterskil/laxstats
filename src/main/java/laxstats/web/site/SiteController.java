package laxstats.web.site;

import javax.validation.Valid;

import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressType;
import laxstats.api.sites.CreateSiteCommand;
import laxstats.api.sites.DeleteSiteCommand;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.UpdateSiteCommand;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
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
@RequestMapping("/sites")
public class SiteController extends ApplicationController {
	private final SiteQueryRepository siteRepository;

	@Autowired
	public SiteController(SiteQueryRepository siteRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.siteRepository = siteRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("sites", siteRepository.findAll());
		return "sites/index";
	}

	@RequestMapping(value = "/{siteId}", method = RequestMethod.GET)
	public String show(@PathVariable String siteId, Model model) {
		final SiteEntry site = siteRepository.findOne(siteId);
		model.addAttribute("site", site);
		return "sites/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newSite(Model model) {
		final SiteForm form = new SiteForm();
		model.addAttribute("form", form);
		return "sites/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createSite(@ModelAttribute("form") @Valid SiteForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "sites/new";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final SiteId identifier = new SiteId();
		final String addressId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final AddressDTO address = new AddressDTO();
		address.setId(addressId);
		address.setAddressType(AddressType.SITE);
		address.setAddress1(form.getAddress1());
		address.setAddress2(form.getAddress2());
		address.setCity(form.getCity());
		address.setRegion(form.getRegion());
		address.setPostalCode(form.getPostalCode());
		address.setPrimary(true);
		address.setDoNotUse(false);
		address.setCreatedAt(now);
		address.setCreatedBy(user);
		address.setModifiedAt(now);
		address.setModifiedBy(user);

		final SiteDTO dto = new SiteDTO();
		dto.setSiteId(identifier);
		dto.setName(form.getName());
		dto.setStyle(form.getStyle());
		dto.setSurface(form.getSurface());
		dto.setDirections(form.getDirections());
		dto.setAddress(address);
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);

		final CreateSiteCommand payload = new CreateSiteCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/sites";
	}

	@RequestMapping(value = "/{siteId}/edit", method = RequestMethod.GET)
	public String editSite(@PathVariable String siteId, Model model) {
		final SiteEntry site = siteRepository.findOne(siteId);
		final SiteForm form = new SiteForm();

		form.setName(site.getName());
		form.setStyle(site.getStyle());
		form.setSurface(site.getSurface());
		form.setDirections(site.getDirections());

		form.setAddress1(site.getAddress().getAddress1());
		form.setAddress2(site.getAddress().getAddress2());
		form.setCity(site.getAddress().getCity());
		form.setRegion(site.getAddress().getRegion());
		form.setPostalCode(site.getAddress().getPostalCode());

		model.addAttribute("form", form);
		return "sites/edit";
	}

	@RequestMapping(value = "/{siteId}", method = RequestMethod.PUT)
	public String updateSite(@ModelAttribute("form") @Valid SiteForm form,
			@PathVariable String siteId, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "sites/edit";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final SiteEntry site = siteRepository.findOne(siteId);
		final SiteId identifier = new SiteId(siteId);

		final AddressDTO address = new AddressDTO();
		address.setId(site.getAddress().getId());
		address.setAddressType(AddressType.SITE);
		address.setAddress1(form.getAddress1());
		address.setAddress2(form.getAddress2());
		address.setCity(form.getCity());
		address.setRegion(form.getRegion());
		address.setPostalCode(form.getPostalCode());
		address.setPrimary(true);
		address.setDoNotUse(false);
		address.setModifiedAt(now);
		address.setModifiedBy(user);

		final SiteDTO dto = new SiteDTO();
		dto.setSiteId(identifier);
		dto.setName(form.getName());
		dto.setStyle(form.getStyle());
		dto.setSurface(form.getSurface());
		dto.setAddress(address);
		dto.setDirections(form.getDirections());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);

		final UpdateSiteCommand payload = new UpdateSiteCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/sites";
	}

	@RequestMapping(value = "/{siteId}", method = RequestMethod.DELETE)
	public String deleteSite(@PathVariable String siteId) {
		final SiteId identifier = new SiteId(siteId);
		final DeleteSiteCommand payload = new DeleteSiteCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/sites";
	}
}
