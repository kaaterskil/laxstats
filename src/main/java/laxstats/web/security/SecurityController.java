package laxstats.web.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index() {
		return "security/index";
	}

	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public String login(@ModelAttribute("login") @Valid LoginForm form,
	// BindingResult bindingResult, Model model) {
	// System.out.println("SecurityController#login called");
	// if (bindingResult.hasErrors()) {
	// System.out.println("bindingResult.hasErrors == true");
	// return "security/index";
	// }
	// System.out.println("encrypting password...");
	// final String rawPassword = "";
	// final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	// final String encodedPassword = encoder.encode(form.getPassword());
	// if (encoder.matches(rawPassword, encodedPassword)) {
	// System.out.println("password matched");
	// return "security/index";
	// }
	// System.out.println("password failed");
	// return "security/index";
	// }
}
