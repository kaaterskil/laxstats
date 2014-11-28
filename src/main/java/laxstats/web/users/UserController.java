package laxstats.web.users;

import laxstats.query.users.UserQueryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {

	private UserQueryRepository userRepository;
	
	@Autowired
	public UserController(UserQueryRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", userRepository.findAll());
		return "user/list";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") String id, Model model) {
		model.addAttribute("item", userRepository.findById(id));
		return "user/detail";
	}
}
