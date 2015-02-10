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

	@RequestMapping(value = "/office", method = RequestMethod.GET)
	public String office() {
		return "home/office";
	}
}
