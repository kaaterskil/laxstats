package laxstats.web.site;

import laxstats.api.people.Address;
import laxstats.api.people.AddressType;
import laxstats.api.sites.*;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/sites")
public class SiteController {
    private SiteQueryRepository siteRepository;
    private UserQueryRepository userRepository;
    private CommandBus commandBus;

    @Autowired
    public SiteController(SiteQueryRepository siteRepository,
                          UserQueryRepository userRepository,
                          CommandBus commandBus) {
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
        this.commandBus = commandBus;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("sites", siteRepository.findAll());
        return "sites/index";
    }

    @RequestMapping(value = "/{siteId}", method = RequestMethod.GET)
    public String show(@PathVariable String siteId, Model model) {
        SiteEntry site = siteRepository.findOne(siteId);
        model.addAttribute("site", site);
        return "sites/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newSite(Model model) {
        SiteForm form = new SiteForm();
        model.addAttribute("form", form);
        return "sites/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createSite(@ModelAttribute("form") @Valid SiteForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "sites/new";
        }
        LocalDateTime now = LocalDateTime.now();
        UserEntry user = getCurrentUser();
        SiteId identifier = new SiteId();
        String addressId = IdentifierFactory.getInstance().generateIdentifier();

        Address address = new Address(addressId, null, identifier.toString(), AddressType.SITE, form.getAddress1(), form.getAddress2(), form.getCity(), form.getRegion(), form.getPostalCode(), true, false, user.getId(), now, user.getId(), now);

        SiteDTO dto = new SiteDTO();
        dto.setAddress(address);
        dto.setCreatedAt(now);
        dto.setCreatedBy(user);
        dto.setDirections(form.getDirections());
        dto.setModifiedAt(now);
        dto.setModifiedBy(user);
        dto.setName(form.getName());
        dto.setSiteId(identifier);
        dto.setStyle(form.getStyle());
        dto.setSurface(form.getSurface());

        CreateSiteCommand payload = new CreateSiteCommand(identifier, dto);
        commandBus.dispatch(new GenericCommandMessage<>(payload));
        return "redirect:/sites";
    }

    @RequestMapping(value = "/{siteId}/edit", method = RequestMethod.GET)
    public String editSite(@PathVariable String siteId, Model model) {
        SiteEntry site = siteRepository.findOne(siteId);
        SiteForm form = new SiteForm();

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
    public String updateSite(@ModelAttribute("form") @Valid SiteForm form, @PathVariable String siteId, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "sites/edit";
        }
        LocalDateTime now = LocalDateTime.now();
        UserEntry user = getCurrentUser();
        SiteEntry site = siteRepository.findOne(siteId);
        SiteId identifier = new SiteId(siteId);

        Address address = new Address(site.getAddress().getId(), null, siteId, AddressType.SITE, form.getAddress1(), form.getAddress2(), form.getCity(), form.getRegion(), form.getPostalCode(), true, false, user.getId(), now, user.getId(), now);

        SiteDTO dto = new SiteDTO();
        dto.setAddress(address);
        dto.setCreatedAt(now);
        dto.setCreatedBy(user);
        dto.setDirections(form.getDirections());
        dto.setModifiedAt(now);
        dto.setModifiedBy(user);
        dto.setName(form.getName());
        dto.setSiteId(identifier);
        dto.setStyle(form.getStyle());
        dto.setSurface(form.getSurface());

        UpdateSiteCommand payload = new UpdateSiteCommand(identifier, dto);
        commandBus.dispatch(new GenericCommandMessage<>(payload));
        return "redirect:/sites";
    }

    @RequestMapping(value = "/{siteId}", method = RequestMethod.DELETE)
    public String deleteSite(@PathVariable String siteId) {
        SiteId identifier = new SiteId(siteId);
        DeleteSiteCommand payload = new DeleteSiteCommand(identifier);
        commandBus.dispatch(new GenericCommandMessage<>(payload));
        return "redirect:/sites";
    }

    private UserEntry getCurrentUser() {
        final Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        final String email = ((org.springframework.security.core.userdetails.User) principal)
                .getUsername();
        return userRepository.findByEmail(email);
    }
}
