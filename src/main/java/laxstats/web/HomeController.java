package laxstats.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "home/index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home/index";
	}

	@RequestMapping(value = "/admin/office", method = RequestMethod.GET)
	public String office() {
		return "home/office";
	}

	@RequestMapping(value = "/sitemap", method = RequestMethod.GET)
	public String siteMap() {
		return "home/siteMap";
	}

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public String termsOfUse() {
		return "home/termsOfUse";
	}

	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
	public String privacyPolicy() {
		return "home/privacyPolicy";
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public String subscribe() {
		return "home/subscribe";
	}
}
