package laxstats.web;

import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import org.axonframework.commandhandling.CommandBus;
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
    final Object principal = SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    final String email = ((org.springframework.security.core.userdetails.User) principal)
        .getUsername();
    return userRepository.findByEmail(email);
  }
}
