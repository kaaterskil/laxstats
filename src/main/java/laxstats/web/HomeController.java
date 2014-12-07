package laxstats.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public String index() {
		return "home/index";
	}

	@RequestMapping(value = "/home")
	public String home() {
		return "home/index";
	}

	@RequestMapping(value = "/office")
	public String office() {
		return "home/office";
	}
}
