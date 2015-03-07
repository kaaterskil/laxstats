package laxstats.web;

import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

abstract public class ApplicationController {
	protected final UserQueryRepository userRepository;
	protected final CommandBus commandBus;

	protected ApplicationController(UserQueryRepository userRepository,
			CommandBus commandBus) {
		this.userRepository = userRepository;
		this.commandBus = commandBus;
	}

	protected UserEntry getCurrentUser() {
		final SecurityContext ctx = SecurityContextHolder.getContext();
		// final User user = (User) ctx.getAuthentication().getPrincipal();
		// return userRepository.findByEmail(user.getUsername());
		final String email = (String) ctx.getAuthentication().getPrincipal();
		return userRepository.findByEmail(email);
	}
}
